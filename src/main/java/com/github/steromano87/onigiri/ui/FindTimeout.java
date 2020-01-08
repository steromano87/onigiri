package com.github.steromano87.onigiri.ui;

import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FindTimeout {
    int value();

    ChronoUnit unit() default ChronoUnit.SECONDS;
}
