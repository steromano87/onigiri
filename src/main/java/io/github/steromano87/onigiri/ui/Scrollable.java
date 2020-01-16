package io.github.steromano87.onigiri.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Interface that defines all the classes that can perform a scroll to an underneath element.
 */
public interface Scrollable {
    /**
     * Scrolls to the given element (or does nothing if the element is already in the current viewport).
     * @param target the element to scroll to
     * @throws NoSuchElementException if the element is not found in the current DOM
     */
    void scrollTo(WebElement target) throws NoSuchElementException;

    /**
     * Scrolls to the element defined in the {@link By} expression (or does nothing if the element
     * is already in the current viewport).
     * @param targetBy the find expression of the element to scroll to
     * @throws NoSuchElementException if the element is not found in the current DOM
     */
    void scrollTo(By targetBy) throws NoSuchElementException;
}
