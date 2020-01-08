package com.github.steromano87.onigiri.enhancers;

import java.lang.reflect.Method;

public interface BeforeMethodEnhancer extends Enhancer {
    default boolean isApplicableBefore(Object target, Method originalMethod, Method overriddenMethod, Object... args) {
        return true;
    }

    void applyBefore(Object target, Method originalMethod, Method overriddenMethod, Object... args) throws Throwable;
}
