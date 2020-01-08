package com.github.steromano87.onigiri.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;

/**
 * The main interface for all the extended WebElements defined in Onigiri.
 *
 * This interface must be implemented by all the extended WebElements defined in Onigiri.
 */
public interface ExtendedElement extends WebElement, Locatable, SearchContext, TakesScreenshot, WrapsDriver, WrapsElement {
    void setWrappedElement(WebElement wrappedElement);
}
