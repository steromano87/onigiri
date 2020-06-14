package io.github.steromano87.onigiri.enhancers;

import io.github.steromano87.onigiri.utils.Proxies;
import javassist.util.proxy.MethodHandler;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class used to fetch and apply the available enhancers to an instance of a
 * {@link io.github.steromano87.onigiri.ui.Page} or a {@link io.github.steromano87.onigiri.ui.Section}.
 * <p>
 * This class (that leverages Javassist's {@link MethodHandler}) scans the current context to
 * instantiate one instance of each available enhancer. Each instance wil be bound to a particular
 * {@link io.github.steromano87.onigiri.ui.Page} instance, so subsequent calls to an enhancer
 * can use previously saved data.
 *
 * @see MethodHandler
 */
public class EnhancersApplier implements MethodHandler {
    private List<Enhancer> beforeMethodEnhancers;
    private List<Enhancer> afterMethodEnhancers;

    private static final Logger logger = LoggerFactory.getLogger(EnhancersApplier.class);

    private static final Set<Class<? extends Enhancer>> enhancerClasses;

    static {
        // Get all subclasses of Enhancer interface
        enhancerClasses = new Reflections(
            new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forClassLoader())
                    .setScanners(new SubTypesScanner())
        ).getSubTypesOf(Enhancer.class).stream()
                .filter(e -> !Modifier.isAbstract(e.getModifiers()))
                .collect(Collectors.toSet());
    }

    /**
     * Standard constructor
     */
    public EnhancersApplier() {
        // Create all the enhancer instances
        Set<Enhancer> enhancers = new HashSet<>();
        for (Class<? extends Enhancer> enhancerClass : enhancerClasses) {
            try {
                enhancers.add(enhancerClass.getConstructor().newInstance());
            } catch (NoSuchMethodException | InstantiationException |
                    IllegalAccessException | InvocationTargetException exc) {
                logger.warn("Enhancer {} could not be instantiated, skipping...", enhancerClass.getName());
            }
        }

        // Split before and after enhancers in their respective lists
        this.beforeMethodEnhancers = this.filterAndSortEnhancers(enhancers, BeforeMethodEnhancer.class);
        this.afterMethodEnhancers = this.filterAndSortEnhancers(enhancers, AfterMethodEnhancer.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        this.applyBeforeMethodEnhancers(self, thisMethod, proceed, args);
        Object output = proceed.invoke(self, args);
        this.applyAfterMethodEnhancers(self, thisMethod, proceed, args);
        return output;
    }

    /**
     * Applies all the available instances of {@link BeforeMethodEnhancer}
     *
     * @param target the target class for enhancer invocation
     * @param originalMethod the original method
     * @param overriddenMethod the Javassist overridden method
     * @param args the method arguments
     * @throws Throwable if the method invocation throws a generic exception or error
     */
    private void applyBeforeMethodEnhancers(Object target, Method originalMethod,
                                            Method overriddenMethod, Object... args) throws Throwable {
        for (Enhancer enhancer : this.beforeMethodEnhancers) {
            BeforeMethodEnhancer beforeMethodEnhancer = (BeforeMethodEnhancer) enhancer;
            if (beforeMethodEnhancer.isApplicableBefore(target, originalMethod, overriddenMethod, args)) {
                logger.debug(
                        "Applying {} enhancer before method {}.{}",
                        beforeMethodEnhancer.getClass().getName(),
                        Proxies.getUnproxiedClass(target).getName(),
                        originalMethod.getName()
                );
                beforeMethodEnhancer.applyBefore(target, originalMethod, overriddenMethod, args);
            }
        }
    }

    /**
     * Applies all the available instances of {@link AfterMethodEnhancer}
     *
     * @param target the target class for enhancer invocation
     * @param originalMethod the original method
     * @param overriddenMethod the Javassist overridden method
     * @param args the method arguments
     * @throws Throwable if the method invocation throws a generic exception or error
     */
    private void applyAfterMethodEnhancers(Object target, Method originalMethod,
                                           Method overriddenMethod, Object... args) throws Throwable {
        for (Enhancer enhancer : afterMethodEnhancers) {
            AfterMethodEnhancer afterMethodEnhancer = (AfterMethodEnhancer) enhancer;
            if (afterMethodEnhancer.isApplicableAfter(target, originalMethod, overriddenMethod, args)) {
                logger.debug(
                        "Applying {} enhancer after method {}.{}",
                        afterMethodEnhancer.getClass().getName(),
                        Proxies.getUnproxiedClass(target).getName(),
                        originalMethod.getName()
                );
                afterMethodEnhancer.applyAfter(target, originalMethod, overriddenMethod, args);
            }
        }
    }

    /**
     * Retrieves the priority for a {@link BeforeMethodEnhancer}.
     *
     * The value is retrieved by inspecting the {@link BeforeMethodPriority} annotation.
     * If this annotation is not present, a default value of 100 is assumed.
     *
     * @param target the enhancer to be scanned
     * @return the enhancer priority
     *
     * @see BeforeMethodPriority
     */
    private int getBeforeMethodPriority(AnnotatedElement target) {
        if (target.isAnnotationPresent(BeforeMethodPriority.class)) {
            return target.getAnnotation(BeforeMethodPriority.class).value();
        } else {
            return 100;
        }
    }

    /**
     * Retrieves the priority for an {@link AfterMethodEnhancer}.
     *
     * The value is retrieved by inspecting the {@link AfterMethodPriority} annotation.
     * If this annotation is not present, a default value of 100 is assumed.
     *
     * @param target the enhancer to be scanned
     * @return the enhancer priority
     *
     * @see AfterMethodPriority
     */
    private int getAfterMethodPriority(AnnotatedElement target) {
        if (target.isAnnotationPresent(AfterMethodPriority.class)) {
            return target.getAnnotation(AfterMethodPriority.class).value();
        } else {
            return 100;
        }
    }

    /**
     * Filters and sorts {@link AfterMethodEnhancer}s from {@link BeforeMethodEnhancer}s, using the declared
     * {@link AfterMethodPriority} or {@link BeforeMethodPriority} respectively.
     *
     * @param availableEnhancers the list of all instances of available enhancers
     * @param enhancerBaseInterface the {@link Enhancer} interface that should be scanned for filtering
     * @return the ordered and filtered list of specific enhancers
     */
    private List<Enhancer> filterAndSortEnhancers(
            Set<Enhancer> availableEnhancers,
            Class<? extends Enhancer> enhancerBaseInterface) {
        List<Enhancer> output = availableEnhancers.stream()
                .filter(e -> enhancerBaseInterface.isAssignableFrom(e.getClass()))
                .sorted(enhancerBaseInterface.equals(BeforeMethodEnhancer.class) ?
                            Comparator.comparingInt(e -> this.getBeforeMethodPriority(e.getClass())).reversed() :
                            Comparator.comparingInt(e -> this.getAfterMethodPriority(e.getClass())))
                .collect(Collectors.toList());

        logger.debug(
                "The following instances of {} class have been detected: {}",
                enhancerBaseInterface.getSimpleName(),
                output.stream()
                        .map(e -> e.getClass().getName())
                        .collect(Collectors.joining(", "))
        );

        return output;
    }
}
