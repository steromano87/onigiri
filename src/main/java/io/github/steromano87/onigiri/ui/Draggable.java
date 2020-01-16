package io.github.steromano87.onigiri.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

/**
 * Interface implemented by {@link WebElement}s that can be dragged over another web element
 * (or to a defined position).
 */
public interface Draggable {
    /**
     * Drags the current element on the given element.
     * @param target the element to be dragged to
     * @throws NoSuchElementException if the element is not found in the current DOM
     */
    void dragTo(WebElement target) throws NoSuchElementException;

    /**
     * Drags the current element on the element identified by the given expression
     * @param targetBy the find expression of the element to be dragged to
     * @throws NoSuchElementException if the element is not found in the current DOM
     */
    void dragTo(By targetBy) throws NoSuchElementException;

    /**
     * Drags the current element to the specified point (expressed in the current viewport)
     * @param targetPoint the point to be dragged to
     */
    void dragTo(Point targetPoint);
}
