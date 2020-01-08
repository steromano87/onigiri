package com.github.steromano87.onigiri.ui.web;

public class NavigationException extends RuntimeException {
    public NavigationException() {
        super();
    }

    public NavigationException(String message) {
        super(message);
    }

    public NavigationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NavigationException(Throwable cause) {
        super(cause);
    }
}
