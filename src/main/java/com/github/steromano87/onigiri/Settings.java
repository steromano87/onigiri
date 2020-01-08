package com.github.steromano87.onigiri;

import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.combined.CombinedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;

public class Settings {
    // Sync properties
    public static final String SYNC_ENABLED = "onigiri.sync.enabled";
    public static final String SYNC_CACHE_ENABLED = "onigiri.sync.cache.enabled";
    public static final String SYNC_WAIT_TIMEOUT = "onigiri.sync.wait.timeout";
    public static final String SYNC_POLLING_INTERVAL = "onigiri.sync.polling.interval";

    // Stopwatch properties
    public static final String STOPWATCH_ENABLED = "onigiri.stopwatch.enabled";

    // Element locators properties
    public static final String ELEMENT_LOCATORS_CACHE_ENABLED = "onigiri.element.locator.cache.enabled";
    public static final String ELEMENT_LOCATOR_TIMEOUT = "onigiri.element.locator.timeout";
    public static final String ELEMENT_LOCATOR_FORCE_EXTENDED = "onigiri.element.locator.force.extended";

    private static Configuration configuration;

    static {
        Parameters parameters = new Parameters();
        CombinedConfigurationBuilder builder = new CombinedConfigurationBuilder()
                .configure(
                        parameters.fileBased()
                            .setFileName("onigiri-settings.xml")
                            .setLocationStrategy(new ClasspathLocationStrategy())
                );

        try {
            configuration = builder.getConfiguration();
        } catch (ConfigurationException exc) {
            configuration = new BaseConfiguration();
        }
    }

    public static Configuration getInstance() {
        return configuration;
    }
}
