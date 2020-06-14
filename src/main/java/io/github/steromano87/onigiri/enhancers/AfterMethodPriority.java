package io.github.steromano87.onigiri.enhancers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the priority (i.e. the order of application) of an {@link AfterMethodEnhancer}.
 *
 * The order of application is calculated using the value passed using this annotation, in direct order.
 *
 * @see AfterMethodEnhancer
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterMethodPriority {
    int value();
}
