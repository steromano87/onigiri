package io.github.steromano87.onigiri.ui;

import java.lang.annotation.*;

/**
 * Marks the elements whose locator result should not be cached for subsequent calls.
 * <p>
 * If a {@link org.openqa.selenium.WebElement} is annotated with {@link Uncached}, then
 * its locator call will be performed on each call to the same element.
 * <p>
 * Note that this behaviour is the more safe approach in case of a rapidly changing page DOM,
 * but in some cases this can increase the execution time considerably (e.g. on mobile native apps)
 * <p>
 * The {@link Uncached} annotation can be used to locally override the general caching setting.
 * If an element uses this annotation, caching will not be performed regardless of the value of the
 * corresponding property.
 *
 * @see io.github.steromano87.onigiri.Settings
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Uncached {
}
