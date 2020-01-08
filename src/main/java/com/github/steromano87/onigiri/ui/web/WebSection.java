package com.github.steromano87.onigiri.ui.web;

import com.github.steromano87.onigiri.handlers.JavascriptHandler;
import com.github.steromano87.onigiri.ui.Section;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public abstract class WebSection extends ExtendedWebElement implements Section {
    @Override
    public void scrollTo(WebElement target) throws NoSuchElementException {
        new JavascriptHandler(this.getWrappedDriver()).scrollToElement(target);
    }

    @Override
    public void scrollTo(By targetBy) throws NoSuchElementException {
        new JavascriptHandler(this.getWrappedDriver()).scrollToElement(this.findElement(targetBy));
    }
}
