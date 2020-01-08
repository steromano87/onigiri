package com.github.steromano87.onigiri.enhancers;

import javassist.util.proxy.Proxy;

public interface Enhancer {
    default String getVanillaClassName(Class<?> proxiedClass) {
        if (Proxy.class.isAssignableFrom(proxiedClass)) {
            return getVanillaClassName(proxiedClass.getSuperclass());
        } else {
            return proxiedClass.getName();
        }
    }
}
