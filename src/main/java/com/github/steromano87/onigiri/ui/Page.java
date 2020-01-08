package com.github.steromano87.onigiri.ui;

import org.openqa.selenium.*;

/**
 * Interface implemented by al the page classes.
 *
 * In the Page Object Model, the Page is the top level container for all underneath objects.
 * The concept of page applies both to web sites or native apps.
 */
public interface Page extends SearchContext, WrapsDriver, TakesScreenshot, Scrollable {
    void setWrappedDriver(WebDriver driver);
}
