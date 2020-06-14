package io.github.steromano87.onigiri.factory;

import io.github.steromano87.onigiri.Settings;
import io.github.steromano87.onigiri.ui.Section;
import io.github.steromano87.onigiri.utils.Proxies;
import io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.*;

public class OnigiriFieldDecorator implements FieldDecorator {
    private final WebDriver driver;
    private final Platform platform;
    private final String automation;
    private final ElementLocatorFactory elementLocatorFactory;
    private final ElementClassSelector selector;

    private static final Set<String> RESERVED_FIELD_NAMES;

    static {
        RESERVED_FIELD_NAMES = new HashSet<>();
        RESERVED_FIELD_NAMES.add("wrappedElement");
    }

    public OnigiriFieldDecorator(SearchContext context) {
        this.driver = WebDriverUnpackUtility.unpackWebDriverFromSearchContext(context);
        this.platform = ((HasCapabilities) this.driver).getCapabilities().getPlatform();
        this.automation = Objects.toString(
                ((HasCapabilities) this.driver).getCapabilities().getCapability("automationName"),
                ""
        );
        this.elementLocatorFactory = new OnigiriElementLocatorFactory(
                context,
                Duration.ofSeconds(Settings.getInstance().getInt(Settings.ELEMENT_LOCATOR_TIMEOUT)),
                this.platform,
                this.automation
        );
        this.selector = new ElementClassSelector(this.platform, this.automation);
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        // If the field name is a reserved one, skip it
        if (RESERVED_FIELD_NAMES.contains(field.getName())) {
            return null;
        }

        // If the field is not implementing the WebElement interface, skip it
        if (!WebElement.class.isAssignableFrom(field.getType()) && !this.isDecoratableList(field)) {
            return null;
        }

        // Create element locator and attach the correct WebElement class
        OnigiriElementLocator locator = (OnigiriElementLocator) this.elementLocatorFactory.createLocator(field);
        if (locator == null) {
            return null;
        }

        // If the element is a Section, use the ad-hoc method
        if (Section.class.isAssignableFrom(field.getType())) {
            return this.decorateSection(field, locator);
        }

        // If the element is a list of Sections, use the ad-hoc method
        if (List.class.isAssignableFrom(field.getType())
                && Section.class.isAssignableFrom(
                (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0])) {
            return this.decorateSectionList(locator);
        }

        // If the field is a list, attach a list element handler
        if (WebElement.class.isAssignableFrom(field.getType())) {
            return Proxies.buildProxyInstance(
                    this.selector.getElementClass(field),
                    new SingleElementMethodHandler(locator, this.driver)
            );
        } else if (List.class.isAssignableFrom(field.getType())) {
            return Proxies.buildProxyInstance(
                    ArrayList.class,
                    new ElementCollectionMethodHandler(locator, this.driver)
            );
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Section> T decorateSection(Field field, OnigiriElementLocator locator) {
        // Create the base section instance
        Class<T> sectionClass = (Class<T>) field.getType();
        return Proxies.buildProxyInstance(
                sectionClass,
                new SingleElementMethodHandler(locator, this.driver)
        );
    }

    @SuppressWarnings("unchecked")
    private <T extends Section> List<T> decorateSectionList(OnigiriElementLocator locator) {
        // In case of list of sections, decoration must be done at runtime since we do not now
        // which SearchContext will be used for each section instance
        return Proxies.buildProxyInstance(
                ArrayList.class,
                new ElementCollectionMethodHandler(locator, this.driver)
        );
    }

    private boolean isDecoratableList(Field field) {
        // If the field is not a list, return false
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        // Type erasure in Java isn't complete. Attempt to discover the generic
        // type of the list.
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }

        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

        // If the list parametrized type is not an instance of WebElement, return false
        if (!WebElement.class.isAssignableFrom((Class<?>) listType)) {
            return false;
        }

        // If the list is not annotated with any FindBy-like annotation, return false
        OnigiriByBuilder builder = new OnigiriByBuilder(this.platform.name(), this.automation);
        builder.setAnnotated(field);
        return builder.buildBy() != null;
    }
}
