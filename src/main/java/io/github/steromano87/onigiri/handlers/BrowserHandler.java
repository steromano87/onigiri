package io.github.steromano87.onigiri.handlers;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.openqa.selenium.*;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BrowserHandler implements HandlesViewport, HasPlatformInformation {
    private WebDriver driver;
    private JavascriptHandler javascriptHandler;

    public BrowserHandler(WebDriver driver) {
        this.driver = driver;
        this.javascriptHandler = new JavascriptHandler(driver);
    }

    @Override
    public float getDevicePixelRatio() {
        return (float) this.javascriptHandler.executeScript("window.devicePixelRatio");
    }

    @Override
    public boolean isHiDpi() {
        return this.getDevicePixelRatio() > 1.0f;
    }

    @Override
    public Dimension getViewportSize() {
        return null;
    }

    public boolean isHeadless() {
        return false;
    }

    public boolean isMaximized() {
        return (boolean) this.javascriptHandler.executeScript("screen.availWidth - window.innerWidth === 0");
    }

    public void maximize() {
        this.driver.manage().window().maximize();
    }

    public void setViewportSize(Dimension viewportSize) {
        this.driver.manage().window().setSize(viewportSize);
    }

    public void setViewportSize(int width, int height) {
        this.setViewportSize(new Dimension(width, height));
    }

    @Override
    public Platform getPlatform() {
        return Platform.getCurrent();
    }

    public String getCurrentTabTitle() {
        return this.driver.getTitle();
    }

    public String getCurrentTabHandle() {
        return this.driver.getWindowHandle();
    }

    public void setCurrentTab(String tabHandle) {
        this.driver.switchTo().window(tabHandle);
    }

    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    public Map<String, String> getCurrentUrlParameters() {
        List<NameValuePair> paramsCouples = URLEncodedUtils.parse(this.getCurrentUrl(), StandardCharsets.UTF_8);

        Map<String, String> params = new HashMap<>();
        for (NameValuePair paramPair : paramsCouples) {
            params.put(paramPair.getName(), paramPair.getValue());
        }

        return params;
    }

    public int getTabCount() {
        return this.driver.getWindowHandles().size();
    }

    public Set<Cookie> getCookies() {
        return this.driver.manage().getCookies();
    }

    public void addCookie(Cookie cookie) {
        this.driver.manage().addCookie(cookie);
    }

    public Cookie getCookie(String cookieName) {
        return this.driver.manage().getCookieNamed(cookieName);
    }

    public void deleteCookies() {
        this.driver.manage().deleteAllCookies();
    }

    public void deleteCookie(String cookieName) {
        this.driver.manage().deleteCookieNamed(cookieName);
    }

    public void visit(String url) {
        this.driver.navigate().to(url);
    }

    public void visit(URL url) {
        this.driver.navigate().to(url);
    }

    public void refresh() {
        this.driver.navigate().refresh();
    }
}
