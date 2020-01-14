package io.github.steromano87.onigiri.test.junit.web;

import io.github.steromano87.onigiri.factory.PageBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.util.Objects;


public abstract class BaseWebTest {
    protected static BrowserWebDriverContainer<?> container;

    protected WebDriver driver;
    protected PageBuilder builder;

    @BeforeAll
    static void beforeAll() {
        if (System.getProperty("dockerized").equals("1")) {
            container = new BrowserWebDriverContainer<>()
                    .withCapabilities(new ChromeOptions())
                    .withClasspathResourceMapping(
                            "testpages/",
                            "/testpages/",
                            BindMode.READ_ONLY
                    );
            container.start();
        }
    }

    @AfterAll
    static void afterAll() {
        if (System.getProperty("dockerized").equals("1")) {
            container.stop();
        }
    }

    @BeforeEach
    void beforeEach() {
        if (System.getProperty("dockerized").equals("1")) {
            this.driver = Objects.requireNonNull(container.getWebDriver());
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            this.driver = new ChromeDriver(options);
        }

        this.builder = new PageBuilder(this.driver);
    }
}
