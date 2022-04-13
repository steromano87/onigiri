package io.github.steromano87.onigiri.ui;

import io.github.steromano87.onigiri.utils.Proxies;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Abstract base implementation of {@link ExtendedElement}.
 * <p>
 * This abstract class simply defines all the standard {@link WebElement} methods in a common place.
 * <p>
 * All the methods that are specific to the web or mobile part are defined in the
 * related subclasses.
 */
public abstract class AbstractExtendedElement implements ExtendedElement {
    private final Logger logger;
    // The wrapped element
    private WebElement wrappedElement;

    public AbstractExtendedElement() {
        this.logger = LoggerFactory.getLogger(Proxies.getUnproxiedClass(this));
    }

    @Override
    public void click() {
        logger.debug("Clicking on element {}...", this.getWrappedElement());
        this.wrappedElement.click();
        logger.info("Clicked on element {}", this.getWrappedElement());
    }

    @Override
    public void submit() {
        logger.debug("Submitting form {}...", this.getWrappedElement());
        this.wrappedElement.submit();
        logger.info("Submitted form {}", this.getWrappedElement());
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        logger.debug("Sending keys '{}'...", String.join("", keysToSend));
        this.wrappedElement.sendKeys(keysToSend);
        logger.info("Sent keys '{}'...", String.join("", keysToSend));
    }

    @Override
    public void clear() {
        logger.debug("Clearing element {}...", this.getWrappedElement());
        this.wrappedElement.clear();
        logger.debug("Cleared element {}", this.getWrappedElement());
    }

    @Override
    public String getTagName() {
        return this.wrappedElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return this.wrappedElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return this.wrappedElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return this.wrappedElement.isEnabled();
    }

    @Override
    public String getText() {
        return this.wrappedElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return this.wrappedElement.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return this.wrappedElement.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return this.wrappedElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return this.wrappedElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return this.wrappedElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return this.wrappedElement.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return this.wrappedElement.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return this.wrappedElement.getScreenshotAs(target);
    }

    @Override
    public WebElement getWrappedElement() {
        return this.wrappedElement;
    }

    @Override
    public void setWrappedElement(WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }
}
