package io.github.steromano87.onigiri.ui;

import java.lang.annotation.*;

/**
 * Marks the elements whose locator result should be cached for subsequent calls.
 * <p>
 * If a {@link org.openqa.selenium.WebElement} is annotated with {@link Cached}, then
 * its locator call will be performed only once. ALl the following usage of the same element
 * will reuse the previously found element.
 * <p>
 * Note that this behaviour can lead to a {@link org.openqa.selenium.StaleElementReferenceException}
 * if the page DOM changes abruptly.
 * <p>
 * The {@link Cached} annotation can be used to locally override the general caching setting.
 * If an element uses this annotation, caching will be performed regardless of the value of the
 * corresponding property.
 *
 * @see io.github.steromano87.onigiri.Settings
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Cached {
}
