package io.github.steromano87.onigiri.ui;

import org.openqa.selenium.*;

/**
 * Interface implemented by al the page classes.
 * <p>
 * In the Page Object Model, the {@link Page} is the top level container for all underneath objects.
 * The concept of page applies both to web sites or native apps.
 * <p>
 * A {@link Page} can contain one or more entries of the following classes:
 * <ul>
 *     <li>{@link WebElement}</li>
 *     <li>{@link Section}</li>
 *     <li>{@link Page}</li>
 * </ul>
 * The latter case is used to model pages with one or more iframes inside. Since an iframe is basically
 * another DOM (i.e. another page) inside a container page, defining a {@link Page} inside
 * another {@link Page} automatically triggers a context switch when polling the element of the
 * inner page.
 * <p>
 * Since the {@link Page} is also a {@link SearchContext}, it can perform an element search using
 * either the default context (the base DOM) or a nested DOM (e.g. an iframe).
 */
public interface Page extends SearchContext, WrapsDriver, TakesScreenshot, Scrollable {
    /**
     * Sets the driver used by the page to performs element searches.
     * @param driver the current driver
     */
    void setWrappedDriver(WebDriver driver);
}
