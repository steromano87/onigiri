package io.github.steromano87.onigiri.enhancers;

import java.lang.reflect.Method;

public interface AfterMethodEnhancer extends Enhancer {
    default boolean isApplicableAfter(Object target, Method originalMethod, Method overriddenMethod, Object... args) {
        return true;
    }

    void applyAfter(Object target, Method originalMethod, Method overriddenMethod, Object... args) throws Throwable;
}
