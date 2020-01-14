package io.github.steromano87.onigiri.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.List;

public class ElementCollectionMethodHandler extends ElementMethodHandler {
    public ElementCollectionMethodHandler(OnigiriElementLocator locator, WebDriver driver) {
        super(locator, driver);
    }

    @Override
    protected Object findAndInvoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        // Search the element and apply the method to it
        List<WebElement> target = this.locator.findElements();

        return thisMethod.invoke(target, args);
    }
}
