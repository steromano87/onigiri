package com.github.steromano87.onigiri.enhancers;

import com.github.steromano87.onigiri.utils.Proxies;
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

public class EnhancersApplier implements MethodHandler {
    private List<Enhancer> beforeMethodEnhancers;
    private List<Enhancer> afterMethodEnhancers;

    private static final Logger logger = LoggerFactory.getLogger(EnhancersApplier.class);

    private static final Set<Class<? extends Enhancer>> enhancerClasses;

    static {
        // Get all subclasses of Enhancer interface
        enhancerClasses = new Reflections(
            new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forJavaClassPath())
                    .setScanners(new SubTypesScanner())
        ).getSubTypesOf(Enhancer.class).stream()
                .filter(e -> !Modifier.isAbstract(e.getModifiers()))
                .collect(Collectors.toSet());
    }

    public EnhancersApplier() {
        // Create all the enhancer instances
        Set<Enhancer> enhancers = new HashSet<>();
        for (Class<? extends Enhancer> enhancerClass : enhancerClasses) {
            try {
                enhancers.add(enhancerClass.getConstructor().newInstance());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exc) {
                logger.warn(String.format("Enhancer %s could not be instantiated, skipping...", enhancerClass.getName()));
            }
        }

        // Split before and after enhancers in their respective lists
        this.beforeMethodEnhancers = enhancers.stream()
                .filter(e -> BeforeMethodEnhancer.class.isAssignableFrom(e.getClass()))
                .sorted(Comparator.comparingInt(e -> this.getBeforeMethodPriority(e.getClass())))
                .collect(Collectors.toList());
        logger.debug("The following BeforeMethod enhancers have been detected: " +
                this.beforeMethodEnhancers.stream()
                        .map(e -> e.getClass().getName())
                        .collect(Collectors.joining(", "))
        );

        this.afterMethodEnhancers = enhancers.stream()
                .filter(e -> AfterMethodEnhancer.class.isAssignableFrom(e.getClass()))
                .sorted(Comparator.comparingInt(e -> this.getAfterMethodPriority(e.getClass())))
                .collect(Collectors.toList());
        logger.debug("The following AfterMethod enhancers have been detected: " +
                this.afterMethodEnhancers.stream()
                        .map(e -> e.getClass().getName())
                        .collect(Collectors.joining(", "))
        );
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        this.applyBeforeMethodEnhancers(self, thisMethod, proceed, args);
        Object output = proceed.invoke(self, args);
        this.applyAfterMethodEnhancers(self, thisMethod, proceed, args);
        return output;
    }

    private void applyBeforeMethodEnhancers(Object target, Method originalMethod, Method overriddenMethod, Object... args) throws Throwable {
        for (Enhancer enhancer : this.beforeMethodEnhancers) {
            BeforeMethodEnhancer beforeMethodEnhancer = (BeforeMethodEnhancer) enhancer;
            if (beforeMethodEnhancer.isApplicableBefore(target, originalMethod, overriddenMethod, args)) {
                logger.debug(
                        String.format(
                                "Applying %s enhancer before method %s.%s",
                                beforeMethodEnhancer.getClass().getName(),
                                Proxies.getUnproxiedClass(target).getName(),
                                originalMethod.getName())
                );
                beforeMethodEnhancer.applyBefore(target, originalMethod, overriddenMethod, args);
            }
        }
    }

    private void applyAfterMethodEnhancers(Object target, Method originalMethod, Method overriddenMethod, Object... args) throws Throwable {
        for (Enhancer enhancer : afterMethodEnhancers) {
            AfterMethodEnhancer afterMethodEnhancer = (AfterMethodEnhancer) enhancer;
            if (afterMethodEnhancer.isApplicableAfter(target, originalMethod, overriddenMethod, args)) {
                logger.debug(
                        String.format(
                                "Applying %s enhancer after method %s.%s",
                                afterMethodEnhancer.getClass().getName(),
                                Proxies.getUnproxiedClass(target).getName(),
                                originalMethod.getName())
                );
                afterMethodEnhancer.applyAfter(target, originalMethod, overriddenMethod, args);
            }
        }
    }

    private int getBeforeMethodPriority(AnnotatedElement target) {
        if (target.isAnnotationPresent(BeforeMethodPriority.class)) {
            return target.getAnnotation(BeforeMethodPriority.class).value();
        } else {
            return 100;
        }
    }

    private int getAfterMethodPriority(AnnotatedElement target) {
        if (target.isAnnotationPresent(AfterMethodPriority.class)) {
            return target.getAnnotation(AfterMethodPriority.class).value();
        } else {
            return 100;
        }
    }
}
