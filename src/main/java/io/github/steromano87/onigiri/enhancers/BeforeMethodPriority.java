package io.github.steromano87.onigiri.enhancers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the priority (i.e. the order of application) of a {@link BeforeMethodEnhancer}.
 *
 * The order of application is calculated using the value passed using this annotation, in reversed order.
 *
 * @see BeforeMethodEnhancer
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeMethodPriority {
    int value();
}
