package io.github.steromano87.onigiri.utils;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;

public class Proxies {
    @SuppressWarnings("unchecked")
    public static <T> T buildProxyInstance(Class<T> baseClass, MethodHandler handler) {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(baseClass);
        try {
            return (T) factory.create(new Class[0], new Object[0], handler);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    public static Class<?> getUnproxiedClass(Class<?> proxiedClass) {
        if (Proxy.class.isAssignableFrom(proxiedClass)) {
            return getUnproxiedClass(proxiedClass.getSuperclass());
        } else {
            return proxiedClass;
        }
    }

    public static Class<?> getUnproxiedClass(Object proxied) {
        return getUnproxiedClass(proxied.getClass());
    }

    public static Throwable getOriginalException(Throwable throwable) {
        if (InvocationTargetException.class.isAssignableFrom(throwable.getCause().getClass())) {
            return getOriginalException(throwable.getCause());
        } else {
            return throwable;
        }
    }
}
