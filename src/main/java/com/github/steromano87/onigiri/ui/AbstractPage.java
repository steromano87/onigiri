package com.github.steromano87.onigiri.ui;

import com.github.steromano87.onigiri.Settings;
import com.github.steromano87.onigiri.enhancers.syncing.SyncTimeout;
import com.github.steromano87.onigiri.utils.Proxies;
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
                Settings.getInstance().getInt(Settings.SYNC_POLLING_INTERVAL)
        );
    }

    protected WebDriverWait getWaiter(int waitTimeout) {
        return this.getWaiter(
                waitTimeout,
                Settings.getInstance().getInt(Settings.SYNC_POLLING_INTERVAL)
        );
    }

    protected WebDriverWait getWaiter(int waitTimeout, int pollingInterval) {
        return (WebDriverWait) new WebDriverWait(this.driver, waitTimeout, pollingInterval)
                .ignoring(StaleElementReferenceException.class);
    }

    private int getDefaultSyncTimeout() {
        if (this.getClass().isAnnotationPresent(SyncTimeout.class)) {
            return (int) Duration.of(
                    this.getClass().getAnnotation(SyncTimeout.class).value(),
                    this.getClass().getAnnotation(SyncTimeout.class).unit()
            ).get(ChronoUnit.SECONDS);
        } else {
            return Settings.getInstance().getInt(Settings.SYNC_WAIT_TIMEOUT);
        }
    }
}
