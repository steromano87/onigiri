package com.github.steromano87.onigiri.enhancers.timing;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Stopwatch {
    String value() default "";
}
