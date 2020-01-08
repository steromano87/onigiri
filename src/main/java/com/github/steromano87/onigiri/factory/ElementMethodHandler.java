package com.github.steromano87.onigiri.factory;

import com.github.steromano87.onigiri.utils.Proxies;
import javassist.util.proxy.MethodHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import java.lang.reflect.Method;

public abstract class ElementMethodHandler implements MethodHandler {
    protected OnigiriElementLocator locator;
    protected WebDriver driver;

    public ElementMethodHandler(OnigiriElementLocator locator, WebDriver driver) {
        this.locator = locator;
        this.driver = driver;
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        try {
            // If the method is a "toString" method, return the output of the locator "toString" method
            if (thisMethod.getName().equals("toString") && args.length == 0) {
                return this.locator.toString();
            }

            // If the method is declared in the Object class, pass it directly to the object without further actions
            if (Object.class.equals(thisMethod.getDeclaringClass())) {
                return proceed.invoke(self, args);
            }

            // If the method is a "getWrappedDriver" method, return directly the inner driver instance
            if (WrapsDriver.class.isAssignableFrom(thisMethod.getDeclaringClass())
                    && thisMethod.getName().equals("getWrappedDriver")) {
                return this.driver;
            }

            // Automatically switch context if an element is marked as Framed
            this.enterFramedContext();
            Object output = this.findAndInvoke(self, thisMethod, proceed, args);
            this.exitFramedContext();
            return output;

        } catch (Throwable exc) {
            // Clean up the exception from the useless InvocationTargetException wrappings and rethrow it
            throw Proxies.getOriginalException(exc);
        }
    }

    protected abstract Object findAndInvoke(Object self, Method thisMethod, Method proceed, Object[] args)
            throws Throwable;

    private void enterFramedContext() {
        for (By by : this.locator.getFramedBys()) {
            WebElement frame = this.driver.findElement(by);
            this.driver.switchTo().frame(frame);
        }
    }

    private void exitFramedContext() {
        this.driver.switchTo().defaultContent();
    }
}
