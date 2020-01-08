package com.github.steromano87.onigiri.ui.web;

import org.openqa.selenium.JavascriptException;

/**
 * Interface implemented by all elements that can trigger an event on themselves.
 */
public interface TriggersEvents {
    void trigger(String event) throws JavascriptException;
}
