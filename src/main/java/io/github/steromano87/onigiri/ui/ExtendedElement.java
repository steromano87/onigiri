package io.github.steromano87.onigiri.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;

/**
 * The main interface for all the extended WebElements defined in Onigiri.
 * <p>
 * {@link ExtendedElement} are a superset of standard Selenium {@link WebElement} that enrich
 * the base interface with additional methods and properties.
 * <p>
 * Since all {@link ExtendedElement} are defined as wrappers to a simple {@link WebElement},
 * the only method defined in the base interface allow the setting of the wrapped element without
 * the need to define a non-zero argument constructor.
 */
public interface ExtendedElement extends WebElement, Draggable,
        Locatable, SearchContext, TakesScreenshot, WrapsDriver, WrapsElement {
    /**
     * Sets the base element that is wrapped by the {@link ExtendedElement}
     *
     * @param wrappedElement the base element that will be wrapped
     */
    void setWrappedElement(WebElement wrappedElement);
}
