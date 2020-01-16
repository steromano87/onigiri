package io.github.steromano87.onigiri.ui.mobile;

import io.github.steromano87.onigiri.ui.AbstractExtendedElement;
import io.github.steromano87.onigiri.ui.ExtendedElement;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.touch.TouchActions;

public class ExtendedMobileElement extends AbstractExtendedElement implements ExtendedElement {
    @Override
    public WebDriver getWrappedDriver() {
        return ((MobileElement) this.getWrappedElement()).getWrappedDriver();
    }

    @Override
    public Coordinates getCoordinates() {
        return ((MobileElement) this.getWrappedElement()).getCoordinates();
    }

    @Override
    public void dragTo(WebElement target) throws NoSuchElementException {
        TouchActions actions = new TouchActions(this.getWrappedDriver());
        actions.longPress(this.getWrappedElement()).moveToElement(target).release().perform();
    }

    @Override
    public void dragTo(By targetBy) throws NoSuchElementException {
        this.dragTo((WebElement) this.getWrappedDriver().findElement(targetBy));
    }

    @Override
    public void dragTo(Point targetPoint) {
        TouchActions actions = new TouchActions(this.getWrappedDriver());
        actions.longPress(this.getWrappedElement())
                .move(targetPoint.getX(), targetPoint.getY()).release().perform();
    }
}
