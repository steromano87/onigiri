package io.github.steromano87.onigiri.test.junit.web;

import io.github.steromano87.onigiri.Settings;
import io.github.steromano87.onigiri.factory.PageBuilder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

@Testcontainers
public abstract class BaseWebTest {
    @Container
    protected BrowserWebDriverContainer<?> container = new BrowserWebDriverContainer<>()
            .withCapabilities(new ChromeOptions())
            .withClasspathResourceMapping(
                    "testpages/",
                    "/testpages/",
                    BindMode.READ_ONLY
            );

    protected WebDriver driver;
    protected PageBuilder builder;

    @BeforeAll
    static void beforeAll() {
        // Install SLF4J JUL bridge
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        System.setProperty(Settings.PAGE_WEB_BASEURL, "file://");
    }

    @BeforeEach
    void beforeEach() {
        this.driver = Objects.requireNonNull(container.getWebDriver());
        this.builder = new PageBuilder(this.driver);
    }
}
