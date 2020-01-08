package com.github.steromano87.onigiri.ui.web;

import com.github.steromano87.onigiri.handlers.JavascriptHandler;
import com.github.steromano87.onigiri.ui.AbstractExtendedElement;
import com.github.steromano87.onigiri.ui.ExtendedElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.remote.RemoteWebElement;

public class ExtendedWebElement extends AbstractExtendedElement implements ExtendedElement, TriggersEvents {
    @Override
    public WebDriver getWrappedDriver() {
        return ((RemoteWebElement) this.getWrappedElement()).getWrappedDriver();
    }

    @Override
    public Coordinates getCoordinates() {
        return ((RemoteWebElement) this.getWrappedElement()).getCoordinates();
    }

    @Override
    public void trigger(String event) {
        new JavascriptHandler(this.getWrappedDriver()).triggerEvent(this, event);
    }
}
