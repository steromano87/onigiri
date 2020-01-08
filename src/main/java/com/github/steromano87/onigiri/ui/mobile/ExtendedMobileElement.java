package com.github.steromano87.onigiri.ui.mobile;

import com.github.steromano87.onigiri.ui.AbstractExtendedElement;
import com.github.steromano87.onigiri.ui.ExtendedElement;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Coordinates;

public class ExtendedMobileElement extends AbstractExtendedElement implements ExtendedElement {
    @Override
    public WebDriver getWrappedDriver() {
        return ((MobileElement) this.getWrappedElement()).getWrappedDriver();
    }

    @Override
    public Coordinates getCoordinates() {
        return ((MobileElement) this.getWrappedElement()).getCoordinates();
    }
}
