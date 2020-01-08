package com.github.steromano87.onigiri.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Interface that defines all the classes that can perform a scroll to an underneath element
 */
public interface Scrollable {
    void scrollTo(WebElement target) throws NoSuchElementException;

    void scrollTo(By targetBy) throws NoSuchElementException;
}
