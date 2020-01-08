package com.github.steromano87.onigiri.handlers;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JavascriptHandler implements JavascriptExecutor {
    private JavascriptExecutor executor;

    private static final String TRIGGER_EVENT_JS_FUNCTION =
            readFunction("javascript/vanilla/triggerEvent.js");
    private static final String SCROLL_TO_ELEMENT_JS_FUNCTION =
            readFunction("javascript/vanilla/scrollToElement.js");


    public JavascriptHandler(WebDriver driver) {
        this.executor = (JavascriptExecutor) driver;
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return this.executor.executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return this.executor.executeAsyncScript(script, args);
    }

    public void triggerEvent(WebElement target, String event) {
        this.executeScript(
                TRIGGER_EVENT_JS_FUNCTION + "\n" +
                        "triggerEvent(arguments[0], arguments[1])", event, target);
    }

    public void scrollToElement(WebElement target) {
        this.executeScript(
                SCROLL_TO_ELEMENT_JS_FUNCTION + "\n" +
                        "scrollToElement(arguments[0])", target);
    }

    private static String readFunction(String path) {
        try {
            InputStream jsStream = Objects.requireNonNull(
                    JavascriptHandler.class.getClassLoader()
                            .getResourceAsStream(path)
            );
            return IOUtils.toString(jsStream, StandardCharsets.UTF_8);
        } catch (IOException exc) {
            throw new RuntimeException(
                    "Cannot read function definition at path " + path,
                    exc
            );
        }
    }
}
