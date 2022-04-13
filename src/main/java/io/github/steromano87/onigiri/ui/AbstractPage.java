package io.github.steromano87.onigiri.ui;

import io.github.steromano87.onigiri.Settings;
import io.github.steromano87.onigiri.enhancers.syncing.SyncTimeout;
import io.github.steromano87.onigiri.utils.Proxies;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


public abstract class AbstractPage implements Page {
    private WebDriver driver;
    private final Logger logger;

    public AbstractPage() {
        this.logger = LoggerFactory.getLogger(Proxies.getUnproxiedClass(this));
    }

    @Override
    public void setWrappedDriver(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public WebDriver getWrappedDriver() {
        return this.driver;
    }

    protected Logger getLogger() {
        return this.logger;
    }

    protected WebDriverWait getWaiter() {
        return this.getWaiter(
                this.getDefaultSyncTimeout(),
                Duration.ofMillis(Settings.getInstance().getInt(Settings.SYNC_DEFAULT_POLLING))
        );
    }

    protected WebDriverWait getWaiter(Duration waitTimeout) {
        return this.getWaiter(
                waitTimeout,
                Duration.ofMillis(Settings.getInstance().getInt(Settings.SYNC_DEFAULT_POLLING))
        );
    }

    protected WebDriverWait getWaiter(Duration waitTimeout, Duration pollingInterval) {
        return (WebDriverWait) new WebDriverWait(this.driver, waitTimeout, pollingInterval)
                .ignoring(StaleElementReferenceException.class);
    }

    private Duration getDefaultSyncTimeout() {
        if (this.getClass().isAnnotationPresent(SyncTimeout.class)) {
            return Duration.of(
                    this.getClass().getAnnotation(SyncTimeout.class).value(),
                    this.getClass().getAnnotation(SyncTimeout.class).unit()
            );
        } else {
            return Duration.ofMillis(Settings.getInstance().getInt(Settings.SYNC_DEFAULT_TIMEOUT));
        }
    }
}
