package io.github.steromano87.onigiri.ui;

import org.openqa.selenium.*;

import java.util.List;

public abstract class AbstractExtendedElement implements ExtendedElement {
    private WebElement wrappedElement;

    public AbstractExtendedElement() {}

    @Override
    public void click() {
        this.wrappedElement.click();
    }

    @Override
    public void submit() {
        this.wrappedElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        this.wrappedElement.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        this.wrappedElement.clear();
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
    public <T extends WebElement> List<T> findElements(By by) {
        return this.wrappedElement.findElements(by);
    }

    @Override
    public <T extends WebElement> T findElement(By by) {
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
