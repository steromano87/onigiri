package io.github.steromano87.onigiri.ui.web;

import io.github.steromano87.onigiri.Settings;
import io.github.steromano87.onigiri.handlers.BrowserHandler;
import io.github.steromano87.onigiri.handlers.JavascriptHandler;
import io.github.steromano87.onigiri.ui.AbstractPage;
import io.github.steromano87.onigiri.ui.Page;
import io.github.steromano87.onigiri.utils.Proxies;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WebPage extends AbstractPage implements Page, Visitable, HoldsTabHandleReference {
    private BrowserHandler browserHandler;
    private JavascriptHandler javascriptHandler;
    private String tabHandle;

    @Override
    public void setWrappedDriver(WebDriver driver) {
        super.setWrappedDriver(driver);
        this.browserHandler = new BrowserHandler(driver);
        this.javascriptHandler = new JavascriptHandler(driver);
    }

    @Override
    public <T extends WebElement> List<T> findElements(By by) {
        return this.getWrappedDriver().findElements(by);
    }

    @Override
    public <T extends WebElement> T findElement(By by) {
        return this.getWrappedDriver().findElement(by);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return ((TakesScreenshot) this.getWrappedDriver()).getScreenshotAs(target);
    }

    @Override
    public void visit() throws NavigationException {
        this.visit(new HashMap<>());
    }

    @Override
    public void visit(Map<String, String> parameters) throws NavigationException {
        if (!Proxies.getUnproxiedClass(this).isAnnotationPresent(PageUrl.class)) {
            throw new NavigationException(
                    "Cannot visit the current page, no page URL has been defined. " +
                            "To allow direct page navigation, please add a @PageUrl annotation on this page class"
            );
        }

        String navigationUrl = Proxies.getUnproxiedClass(this).getAnnotation(PageUrl.class).value();
        UrlParameter[] defaultParameters = Proxies.getUnproxiedClass(this).getAnnotation(PageUrl.class).parameters();

        // Get the base URL from properties if the navigation URL starts with a slash
        if (navigationUrl.startsWith("/")) {
            navigationUrl = Settings.getInstance().getString(Settings.PAGE_BASE_URL) + navigationUrl;
        }

        // Build the final URL adding the page parameters
        try {
            URIBuilder uriBuilder = new URIBuilder(navigationUrl);

            for (UrlParameter parameter : defaultParameters) {
                String parameterValue = parameters.getOrDefault(parameter.name(), parameter.defaultValue());
                uriBuilder.addParameter(parameter.name(), parameterValue);
            }

            navigationUrl = uriBuilder.toString();
        } catch (URISyntaxException exc) {
            throw new NavigationException("Invalid navigation URL: " + navigationUrl, exc);
        }

        this.getLogger().info("Navigating to {}", navigationUrl);
        this.getBrowserHandler().visit(navigationUrl);
    }

    @Override
    public void returnToOwnTab() throws NavigationException {
        if (this.tabHandle != null) {
            this.getBrowserHandler().setCurrentTab(this.tabHandle);
        } else {
            throw new NavigationException(
                    "No tab handle has been registered for the current page"
            );
        }
    }

    @Override
    public void registerTabHandle() {
        this.tabHandle = this.getBrowserHandler().getCurrentTabHandle();
    }

    @Override
    public void scrollTo(WebElement target) throws NoSuchElementException {
        this.getJavascriptHandler().scrollToElement(target);
    }

    @Override
    public void scrollTo(By targetBy) throws NoSuchElementException {
        WebElement target = this.findElement(targetBy);
        this.scrollTo(target);
    }

    protected BrowserHandler getBrowserHandler() {
        return this.browserHandler;
    }

    protected JavascriptHandler getJavascriptHandler() {
        return this.javascriptHandler;
    }
}
