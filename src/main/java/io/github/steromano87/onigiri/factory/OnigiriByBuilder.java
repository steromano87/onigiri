package io.github.steromano87.onigiri.factory;


import io.github.steromano87.onigiri.Settings;
import io.github.steromano87.onigiri.ui.Cached;
import io.github.steromano87.onigiri.ui.Uncached;
import io.appium.java_client.pagefactory.DefaultElementByBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OnigiriByBuilder extends DefaultElementByBuilder {
    public OnigiriByBuilder(String platform, String automation) {
        super(platform, automation);
    }

    public OnigiriByBuilder(AnnotatedElement element, String platform, String automation) {
        super(platform, automation);

        // Force the underlying annotated element container to contain the given element
        try {
            Method setAnnotatedMethod = this.annotatedElementContainer.getClass()
                    .getDeclaredMethod("setAnnotated", AnnotatedElement.class);
            setAnnotatedMethod.setAccessible(true);
            setAnnotatedMethod.invoke(this.annotatedElementContainer, element);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public boolean isLookupCached() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        boolean isElementCacheExplicitlyEnabled = annotatedElement.isAnnotationPresent(Cached.class);
        boolean isElementCacheExplicitlyDisabled = annotatedElement.isAnnotationPresent(Uncached.class);
        boolean isElementCacheGloballyEnabled = Settings.getInstance()
                .getBoolean(Settings.ELEMENT_LOCATOR_CACHED);

        if (isElementCacheGloballyEnabled) {
            return !isElementCacheExplicitlyDisabled;
        } else {
            return isElementCacheExplicitlyEnabled;
        }
    }
}
