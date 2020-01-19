package io.github.steromano87.onigiri.enhancers;

/**
 * The main interface that all derived enhancers classes must implement.
 * <p>
 * Enhancers application is controlled by two factors:
 * <ul>
 *     <li>the inheritance from the {@link BeforeMethodEnhancer} and/or the {@link AfterMethodEnhancer}
 *     interfaces, to determine whether the enhancer is applied respectively before or after
 *     the real method call</li>
 *     <li>the presence of the {@link BeforeMethodPriority} and/or the {@link AfterMethodPriority}
 *     annotations, that determine the order of appliance in case of multiple enhancers</li>
 * </ul>
 *
 * @see BeforeMethodEnhancer
 * @see AfterMethodEnhancer
 * @see BeforeMethodPriority
 * @see AfterMethodPriority
 */
public interface Enhancer {
}
