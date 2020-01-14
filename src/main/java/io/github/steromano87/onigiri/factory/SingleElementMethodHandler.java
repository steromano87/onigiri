package io.github.steromano87.onigiri.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

public class SingleElementMethodHandler extends ElementMethodHandler {
    public SingleElementMethodHandler(OnigiriElementLocator locator, WebDriver driver) {
        super(locator, driver);
    }

    @Override
    protected Object findAndInvoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        // Search the element and apply the method to it
        WebElement target = this.locator.findElement();

        return thisMethod.invoke(target, args);
    }
}
