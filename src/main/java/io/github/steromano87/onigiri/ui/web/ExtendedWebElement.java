package io.github.steromano87.onigiri.ui.web;

import io.github.steromano87.onigiri.handlers.JavascriptHandler;
import io.github.steromano87.onigiri.ui.AbstractExtendedElement;
import io.github.steromano87.onigiri.ui.ExtendedElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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

    @Override
    public void dragTo(WebElement target) throws NoSuchElementException {
        Actions actions = new Actions(this.getWrappedDriver());
        actions.dragAndDrop(this.getWrappedElement(), target);
    }

    @Override
    public void dragTo(By targetBy) throws NoSuchElementException {
        this.dragTo((WebElement) this.getWrappedDriver().findElement(targetBy));
    }

    @Override
    public void dragTo(Point targetPoint) {
        // TODO: check if the offset is correctly calculated
        int xOffset = targetPoint.getX() - this.getWrappedElement().getLocation().getX();
        int yOffset = this.getWrappedElement().getLocation().getY() - targetPoint.getY();
        Actions actions = new Actions(this.getWrappedDriver());
        actions.dragAndDropBy(this.getWrappedElement(), xOffset, yOffset);
    }
}
