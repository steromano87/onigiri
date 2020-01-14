package io.github.steromano87.onigiri.ui.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the URL of a specific page
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageUrl {
    String value();

    UrlParameter[] parameters() default {};
}
