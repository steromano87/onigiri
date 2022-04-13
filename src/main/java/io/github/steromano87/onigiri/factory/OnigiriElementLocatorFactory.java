package io.github.steromano87.onigiri.factory;

import io.github.steromano87.onigiri.ui.FindTimeout;
import io.appium.java_client.pagefactory.locator.CacheableElementLocatorFactory;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import io.github.steromano87.onigiri.ui.web.Framed;
import io.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

public class OnigiriElementLocatorFactory implements CacheableElementLocatorFactory {
    private final SearchContext searchContext;
    private final Duration findTimeout;
    private final OnigiriByBuilder builder;
    private final ElementClassSelector selector;

    private final Platform platform;

    public OnigiriElementLocatorFactory(
            SearchContext context,
            Duration findTimeout,
            Platform platform,
            String automation) {
        this.searchContext = context;
        this.findTimeout = findTimeout;
        this.builder = new OnigiriByBuilder(platform.name(), automation);
        this.selector = new ElementClassSelector(platform);
        this.platform = platform;
    }

    @Override
    public CacheableLocator createLocator(Field field) {
        Duration overriddenTimeout;

        if (field.isAnnotationPresent(FindTimeout.class)) {
            overriddenTimeout = Duration.of(
                    field.getAnnotation(FindTimeout.class).value(),
                    field.getAnnotation(FindTimeout.class).unit()
            );
        } else {
            overriddenTimeout = this.findTimeout;
        }

        this.builder.setAnnotated(field);
        By byLocator = this.builder.buildBy();

        return Optional.ofNullable(byLocator)
                .map(by -> new OnigiriElementLocator(
                        this.platform,
                        this.searchContext,
                        by,
                        builder.isLookupCached(),
                        overriddenTimeout,
                        this.selector.getElementClass(field),
                        this.getFrameBys(field)
                ))
                .orElse(null);
    }

    @Override
    public CacheableLocator createLocator(AnnotatedElement annotatedElement) {
        return this.createLocator((Field) annotatedElement);
    }

    private By[] getFrameBys(Field field) {
        if (WebPage.class.isAssignableFrom(field.getType())) {
            this.builder.setAnnotated(field);
            return new By[] {this.builder.buildBy()};
        }

        if (field.isAnnotationPresent(Framed.class)) {
            this.builder.setAnnotated(field);
            FindBy[] findBys = field.getAnnotation(Framed.class).value();
            return Arrays.stream(findBys)
                    .map(f -> new FindBy.FindByBuilder().buildIt(f, field))
                    .toArray(By[]::new);
        }

        return new By[] {};
    }
}
