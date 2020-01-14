package io.github.steromano87.onigiri.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

/**
 * Interface implemented by WebElements that can be dragged over another web element (or to a defined position)
 */
public interface Draggable {
    void dragTo(WebElement target);

    void dragTo(By targetBy);

    void dragTo(Point targetPoint);
}
