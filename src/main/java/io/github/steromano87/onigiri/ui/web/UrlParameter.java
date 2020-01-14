package io.github.steromano87.onigiri.ui.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UrlParameter {
    String name();

    String defaultValue() default "";
}
