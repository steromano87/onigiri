package io.github.steromano87.onigiri.factory;

import io.github.steromano87.onigiri.Settings;
import io.github.steromano87.onigiri.ui.Section;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import io.github.steromano87.onigiri.ui.ExtendedElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnigiriElementLocator implements CacheableLocator {
    private SearchContext searchContext;
    private By by;
    private Duration searchTimeout;
    private boolean shouldCache;
    private Class<? extends WebElement> elementType;
    private WebElement cachedElement;
    private List<WebElement> cachedElementList;
    private By[] framedBys;


    public OnigiriElementLocator(
            SearchContext searchContext,
            By by,
            boolean shouldCache,
            Duration searchTimeout,
            Class<? extends WebElement> targetType,
            By[] framedBys) {
        this.searchContext = searchContext;
        this.by = by;
        this.shouldCache = shouldCache;
        this.searchTimeout = searchTimeout;
        this.elementType = targetType;
        this.framedBys = framedBys;
    }

    @Override
    public WebElement findElement() {
        // If the element cache is enabled and there is a cached element, return this
        if (this.shouldCache && this.cachedElement != null) {
            return this.cachedElement;
        }

        // If the cache is disabled, perform an element search and return the freshly found element
        // First of all, wait for the given element to appear
        WebElement foundElement = this.castToProperElementClass(
                this.getWaiter().until(context -> context.findElement(this.by))
        );

        // If cache is enabled, save the found element into it
        if (this.shouldCache) {
            this.cachedElement = foundElement;
        }

        return foundElement;
    }

    @Override
    public List<WebElement> findElements() {
        // If the cache is enabled and there is a saved element list in cache. return it
        if (this.shouldCache && this.cachedElementList != null) {
            return this.cachedElementList;
        }

        // If the cache is disabled or the list is null, do a fresh search of the elements
        List<WebElement> rawFoundElements = this.getWaiter().until(context -> context.findElements(this.by));

        // Perform a element-by-element cast to the destination class
        List<WebElement> foundElements = new ArrayList<>();
        rawFoundElements.forEach(e -> foundElements.add(this.castToProperElementClass(e)));

        // If the cache is enabled, save the found elements into it
        if (this.shouldCache) {
            this.cachedElementList = foundElements;
        }

        return foundElements;
    }

    @Override
    public boolean isLookUpCached() {
        return this.shouldCache;
    }

    public By[] getFramedBys() {
        return framedBys;
    }

    @Override
    public String toString() {
        String template;
        if (this.isLookUpCached()) {
            template = "Located by %s (using cache)";
        } else {
            template = "Located by %s";
        }

        return String.format(template, by);
    }

    private FluentWait<SearchContext> getWaiter() {
        List<Class<? extends Exception>> ignoredExceptions = Arrays.asList(
                NoSuchElementException.class,
                StaleElementReferenceException.class
        );

        return new FluentWait<>(this.searchContext)
                .ignoreAll(ignoredExceptions)
                .withMessage(String.format(
                        "Cannot find any element identified by %s after %d s timeout",
                        this.by.toString(),
                        this.searchTimeout.toMillis()
                ));
    }

    private WebElement castToProperElementClass(WebElement rawElement) {
        WebElement outputElement;
        if (ExtendedElement.class.isAssignableFrom(this.elementType) ||
                Settings.getInstance().getBoolean(Settings.ELEMENT_FORCE_EXTENDED)) {
            try {
                outputElement = this.elementType
                        .getConstructor()
                        .newInstance();
                ((ExtendedElement) outputElement).setWrappedElement(rawElement);

                // If the found element is a section, decorate it on the fly
                if (Section.class.isAssignableFrom(outputElement.getClass())) {
                    PageFactory.initElements(
                            new OnigiriFieldDecorator(rawElement),
                            outputElement
                    );
                }

            } catch (NoSuchMethodException |
                    InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException exc) {
                throw new NoSuchElementException(
                        String.format(
                                "Cannot instantiate an extended element from base element %s",
                                rawElement.toString()
                        ),
                        exc
                );
            }
        } else {
            outputElement = rawElement;
        }

        return outputElement;
    }
}
