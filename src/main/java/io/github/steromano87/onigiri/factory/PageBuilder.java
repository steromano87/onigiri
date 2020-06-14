package io.github.steromano87.onigiri.factory;

import io.github.steromano87.onigiri.enhancers.EnhancersApplier;
import io.github.steromano87.onigiri.ui.Page;
import io.github.steromano87.onigiri.utils.Proxies;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.Objects;

public class PageBuilder {
    private final WebDriver driver;

    public PageBuilder(WebDriver driver) {
        this.driver = driver;
    }

    public <T extends Page> T build(Class<T> pageClass) {
        T pageInstance = Objects.requireNonNull(Proxies.buildProxyInstance(pageClass, new EnhancersApplier()));
        PageFactory.initElements(new OnigiriFieldDecorator(this.driver), pageInstance);
        pageInstance.setWrappedDriver(this.driver);
        return pageInstance;
    }
}
