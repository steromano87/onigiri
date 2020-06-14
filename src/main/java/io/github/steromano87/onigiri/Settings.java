package io.github.steromano87.onigiri;

import io.github.steromano87.onigiri.enhancers.syncing.Synced;
import io.github.steromano87.onigiri.enhancers.syncing.SyncEnhancer;
import io.github.steromano87.onigiri.enhancers.timing.StopwatchEnhancer;
import io.github.steromano87.onigiri.ui.ExtendedElement;
import io.github.steromano87.onigiri.ui.web.PageUrl;
import io.github.steromano87.onigiri.ui.web.WebPage;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.combined.CombinedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;

/**
 * Utility class that holds the overall Onigiri settings.
 * <p>
 * This class serves two main purposes:
 * <ul>
 *     <li>holds a singleton instance of a {@link Configuration} object that stores
 *     the Onigiri settings</li>
 *     <li>hold the list of Onigiri property keys for an easier access throughout the code</li>
 * </ul>
 * <p>
 * The {@link Configuration} item is built as a composite configuration:
 * <ol>
 *     <li>first of all the default values are loaded from the Onigiri factory settings</li>
 *     <li>if a <code>onigiri.properties</code> file is found in the resources folder,
 *     all the properties defined inside this file override the default values</li>
 *     <li>if some property is set as a system property, this property has the highest priority and
 *     overrides the previously defined value</li>
 * </ol>
 */
public class Settings {
    // Sync properties
    /**
     * Whether the sync enhancer is enabled or not in the current project.
     *
     * @see SyncEnhancer
     */
    public static final String SYNC_ENABLED = "onigiri.sync.enabled";

    /**
     * Whether the sync cache is enabled or not in the current project.
     * <p>
     * If the sync cache is enabled, the sync conditions for a page will be evaluated only once
     * per page, speeding up the execution but with a higher risk of stale elements (the page may
     * have changed in the meanwhile).
     * <p>
     * If the sync cache is disabled, the sync conditions will be always evaluated before executing a
     * {@link Synced} method. This will ensure the
     * page is always loaded before performing an action, but the execution speed wil be lower due to
     * the repeated evaluations.
     */
    public static final String SYNC_CACHED = "onigiri.sync.cached";

    /**
     * The default timeout for sync.
     */
    public static final String SYNC_DEFAULT_TIMEOUT = "onigiri.sync.default.timeout";

    /**
     * The default polling interval between two consecutive page sync evaluations.
     */
    public static final String SYNC_DEFAULT_POLLING = "onigiri.sync.default.polling";

    // Stopwatch properties
    /**
     * Whether the stopwatch enhancer is enabled or not in the current project.
     *
     * @see StopwatchEnhancer
     */
    public static final String STOPWATCH_ENABLED = "onigiri.stopwatch.enabled";

    // Element locators properties
    /**
     * Whether the element locator cache is enabled or not in the current project.
     * <p>
     * If the element locator cache is enabled, the element find query is performed only once per element.
     * In the next calls to the element, the previously found {@link org.openqa.selenium.WebElement} is used.
     * This behaviour greatly improves execution speed, but in case of a highly mutable page (i.e.
     * with a strong presence of asynchronous Javascript scripts that dynamically change the page DOM)
     * this can lead to element staleness.
     * <p>
     * If the element locator cache is disabled, every time an element is accessed its find query
     * is recalculated. This ensures that the requested element is always "fresh", but the execution speed
     * may be sensibly reduced.
     */
    public static final String ELEMENT_LOCATOR_CACHED = "onigiri.element.locator.cached";

    /**
     * Default element locator timeout.
     */
    public static final String ELEMENT_LOCATOR_TIMEOUT = "onigiri.element.locator.timeout";

    /**
     * Force the use of Onigiri's {@link ExtendedElement}s instead
     * of the default Selenium ones.
     * <p>
     * If this option is set to <code>true</code>, all the elements declared as plain
     * {@link org.openqa.selenium.WebElement} will be rendered as
     * {@link ExtendedElement}. Elements already declared as
     * {@link ExtendedElement} will not be affected.
     */
    public static final String ELEMENT_FORCE_EXTENDED = "onigiri.element.forceextended";

    // Page properties
    /**
     * Web page base (or host) URL.
     * <p>
     * If a {@link WebPage} is annotated with a
     * {@link PageUrl} and the provided URL starts with a slash (/),
     * then the URL is considered dynamic and will be evaluated using the URL defined in this property
     * as host URL.
     */
    public static final String PAGE_WEB_BASEURL = "onigiri.page.web.baseurl";

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

    /**
     * Private constructor
     */
    private Settings() {}

    /**
     * Retrieves the singleton instance of Onigiri configuration.
     *
     * @return the global instance of Onigiri configuration
     */
    public static Configuration getInstance() {
        return configuration;
    }
}
