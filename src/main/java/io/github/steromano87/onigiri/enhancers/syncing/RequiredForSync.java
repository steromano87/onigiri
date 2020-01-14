package io.github.steromano87.onigiri.enhancers.syncing;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RequiredForSync {
    String failureMessage() default "";

    int priority() default 100;
}
