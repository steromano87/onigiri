package io.github.steromano87.onigiri.factory;

import io.appium.java_client.internal.ElementMap;
import io.github.steromano87.onigiri.ui.ExtendedElement;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ElementClassSelector {
    private Platform platform;
    private String automation;

    public ElementClassSelector(Platform platform, String automation) {
        this.platform = platform;
        this.automation = automation;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends WebElement> getElementClass(Field element) {
        // If the element is not a WebElement instance, return null
        if (!WebElement.class.isAssignableFrom(element.getType()) && !List.class.isAssignableFrom(element.getType())) {
            return null;
        }

        // Type erasure in Java isn't complete. Attempt to discover the generic
        // type of the list.
        if (List.class.isAssignableFrom(element.getType())) {
            return this.getElementClassFromList(element);
        }

        // If the desired class is a simple class, return it directly
        if (!ExtendedElement.class.isAssignableFrom(element.getType())) {
            return ElementMap.getElementClass(this.platform.toString(), this.automation);
        }

        // TODO: extend the logic of the method determination (instead of relying on the declared class only)
        return (Class<? extends ExtendedElement>) element.getType();
    }

    @SuppressWarnings("unchecked")
    private Class<? extends WebElement> getElementClassFromList(Field element) {
        Type genericType = element.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }

        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

        // If the inner element is derived from an Extended element, use the inner list class
        if (ExtendedElement.class.isAssignableFrom((Class<?>) listType)) {
            return (Class<? extends ExtendedElement>) listType;
        }

        // If the list parametrized type is not an instance of WebElement, return null
        if (!WebElement.class.equals(listType)) {
            return null;
        }

        return ElementMap.getElementClass(this.platform.toString(), this.automation);
    }
}
