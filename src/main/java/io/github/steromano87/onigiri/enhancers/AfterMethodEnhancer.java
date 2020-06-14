package io.github.steromano87.onigiri.enhancers;

import java.lang.reflect.Method;

/**
 * Interface implemented by enhancers that are applied after page/section/element method call.
 *
 * @see AfterMethodPriority
 */
public interface AfterMethodEnhancer extends Enhancer {
    /**
     * Determine whether the enhancer can be applied by a specific class, after a specific method or
     * using a specific arguments set.
     *
     * @param target            the target object the enhancer is applied to
     * @param originalMethod    the original (unproxied) method of the target object
     * @param overriddenMethod  the overridden (proxied) method of the target object
     * @param args              the arguments passed to the method of the target object
     * @return                  whether the current enhancer can be applied or not to the target object
     */
    default boolean isApplicableAfter(Object target, Method originalMethod, Method overriddenMethod, Object... args) {
        return true;
    }

    /**
     * Specific action performed by the current enhancer. This method will be overridden in each implementation.
     * @param target            the target object the enhancer is applied to
     * @param originalMethod    the original (unproxied) method of the target object
     * @param overriddenMethod  the overridden (proxied) method of the target object
     * @param args              the arguments passed to the method of the target object
     * @throws Throwable        Generic exception thrown by the enhancer and rethrown to the applier
     */
    void applyAfter(Object target, Method originalMethod, Method overriddenMethod, Object... args) throws Throwable;
}
