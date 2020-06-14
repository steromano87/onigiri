package io.github.steromano87.onigiri.enhancers.syncing;

import io.github.steromano87.onigiri.Settings;
import io.github.steromano87.onigiri.enhancers.BeforeMethodEnhancer;
import io.github.steromano87.onigiri.enhancers.BeforeMethodPriority;
import io.github.steromano87.onigiri.factory.OnigiriByBuilder;
import io.github.steromano87.onigiri.ui.web.HoldsTabHandleReference;
import io.github.steromano87.onigiri.utils.Proxies;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@BeforeMethodPriority(100)
public class SyncingEnhancer implements BeforeMethodEnhancer {
    private boolean isSynced = false;
    private final Map<RequiredForSync, Function<Void, Void>> syncConditions = new TreeMap<>(
            Comparator.comparingInt(RequiredForSync::priority)
    );
    private static final Logger logger = LoggerFactory.getLogger(SyncingEnhancer.class);

    @Override
    public boolean isApplicableBefore(Object target, Method originalMethod, Method overriddenMethod, Object... args) {
        return originalMethod.isAnnotationPresent(Synced.class)
                && WrapsDriver.class.isAssignableFrom(target.getClass())
                && Settings.getInstance().getBoolean(Settings.SYNC_ENABLED);
    }

    @Override
    public void applyBefore(Object target, Method originalMethod, Method overriddenMethod, Object... args)
            throws SyncException {
        // TODO: move sync conditions detection in constructor
        this.buildExplicitSyncConditions(Proxies.getUnproxiedClass(target));
        this.buildImplicitSyncConditions((WrapsDriver) target);

        if ((this.isSyncCacheEnabled() && ! this.isSynced) || !this.isSyncCacheEnabled()) {
            logger.info(
                    String.format(
                            "Triggering sync for method %s.%s",
                            Proxies.getUnproxiedClass(target).getName(),
                            originalMethod.getName()
                    )
            );
            for (Map.Entry<RequiredForSync, Function<Void, Void>> syncCondition : this.syncConditions.entrySet()) {
                syncCondition.getValue().apply(null);
            }

            this.isSynced = true;

            // If the target holds the reference of the current tab, locks it, since it has been synced
            if (HoldsTabHandleReference.class.isAssignableFrom(target.getClass())) {
                logger.debug(String.format("Registering tab handle in %s",
                        Proxies.getUnproxiedClass(target).getName()));
                ((HoldsTabHandleReference) target).registerTabHandle();
            }
        }
    }

    private boolean isSyncCacheEnabled() {
        boolean isSyncCacheExplicitlyEnabled = this.getClass().isAnnotationPresent(EnableSyncCache.class);
        boolean isSyncCacheExplicitlyDisabled = this.getClass().isAnnotationPresent(DisableSyncCache.class);
        boolean isSyncCacheGloballyEnabled = Settings.getInstance().getBoolean(Settings.SYNC_CACHED);

        if (isSyncCacheGloballyEnabled) {
            return !isSyncCacheExplicitlyDisabled;
        } else {
            return isSyncCacheExplicitlyEnabled;
        }
    }

    private int getDefaultSyncTimeout(Object target) {
        if (target.getClass().isAnnotationPresent(SyncTimeout.class)) {
            return (int) Duration.of(
                    this.getClass().getAnnotation(SyncTimeout.class).value(),
                    this.getClass().getAnnotation(SyncTimeout.class).unit()
            ).get(ChronoUnit.SECONDS);
        } else {
            return Settings.getInstance().getInt(Settings.SYNC_DEFAULT_TIMEOUT);
        }
    }

    private void buildImplicitSyncConditions(WrapsDriver target) {
        // Get all the fields annotated with the RequiredForSync annotation
        Class<?> targetClass = Proxies.getUnproxiedClass(target);
        Set<Field> syncFields = Arrays.stream(targetClass.getFields())
                .filter(f -> f.isAnnotationPresent(RequiredForSync.class))
                .collect(Collectors.toSet());
        logger.debug(
                String.format(
                        "%d field sync conditions have been found%s",
                        syncFields.size(),
                        syncFields.size() > 0 ?
                                ": " + syncFields.stream()
                                        .map(Field::getName)
                                        .collect(Collectors.joining(", ")) :
                                ""
                )
        );

        String platformName = ((HasCapabilities) target.getWrappedDriver()).getCapabilities().getPlatform().name();
        String automationName = Objects.toString(((HasCapabilities) target.getWrappedDriver()).getCapabilities()
                    .getCapability("automationName"), "");

        for (Field syncField : syncFields) {
            // Build the locator for each annotated field
            By locator = new OnigiriByBuilder(syncField, platformName, automationName).buildBy();

            Status syncConditionStatus = this.getSyncConditionStatus(syncField);

            // Build the failure message
            final String failureMessage;
            if (syncField.getAnnotation(RequiredForSync.class).failureMessage().equals("")) {
                failureMessage = String.format(
                        syncConditionStatus.getDefaultFailureMessageTemplate(),
                        syncField.getName()
                );
            } else {
                failureMessage = syncField.getAnnotation(RequiredForSync.class).failureMessage();
            }

            // Build the waiter function for each sync condition
            this.syncConditions.put(
                    syncField.getAnnotation(RequiredForSync.class),
                    (Void) -> {
                        try {
                            new WebDriverWait(target.getWrappedDriver(), this.getDefaultSyncTimeout(target))
                                    .ignoring(StaleElementReferenceException.class)
                                    .until(
                                        syncConditionStatus.buildExpectedCondition(locator)
                            );
                        } catch (Exception exc) {
                            throw new SyncException(failureMessage, exc);
                        }

                        return null;
                    }
            );
        }
    }

    private void buildExplicitSyncConditions(AnnotatedElement target) {
        // Find all the methods annotated with the @RequiredForSync annotation
        Class<?> targetClass = Proxies.getUnproxiedClass(target);
        Set<Method> syncMethods = Arrays.stream(targetClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(RequiredForSync.class))
                .collect(Collectors.toSet());
        logger.debug(
                String.format(
                        "%d method sync conditions have been found%s",
                        syncMethods.size(),
                        syncMethods.size() > 0 ?
                                ": " + syncMethods.stream()
                                        .map(Method::getName)
                                        .collect(Collectors.joining(", ")) :
                                ""
                )
        );

        for (Method syncMethod : syncMethods) {
            // Build the failure message
            final String failureMessage;
            if (syncMethod.getAnnotation(RequiredForSync.class).failureMessage().equals("")) {
                failureMessage = String.format(
                        "Execution of synchronization method '%s.%s' failed",
                        syncMethod.getDeclaringClass(),
                        syncMethod.getName()
                );
            } else {
                failureMessage = syncMethod.getAnnotation(RequiredForSync.class).failureMessage();
            }

            this.syncConditions.put(
                    syncMethod.getAnnotation(RequiredForSync.class),
                    (Void) -> {
                        try {
                            syncMethod.invoke(this);
                        } catch (Exception exc) {
                            throw new SyncException(failureMessage, exc);
                        }
                        return null;
                    }
            );
        }
    }

    private Status getSyncConditionStatus(AnnotatedElement target) {
        if (target.isAnnotationPresent(SyncCondition.class)) {
            return target.getAnnotation(SyncCondition.class).value();
        } else {
            return Status.VISIBLE;
        }
    }
}
