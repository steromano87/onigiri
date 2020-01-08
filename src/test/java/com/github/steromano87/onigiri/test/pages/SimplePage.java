package com.github.steromano87.onigiri.test.pages;

import com.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import com.github.steromano87.onigiri.enhancers.syncing.Synced;
import com.github.steromano87.onigiri.ui.web.NavigationException;
import com.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;
import java.util.Objects;

public class SimplePage extends WebPage {
    @RequiredForSync
    @FindBy(css = "h1")
    private WebElement title;

    @FindBy(css = "#main-body")
    private WebElement mainBody;

    @Synced
    public String getTitle() {
        return this.title.getText();
    }

    @Synced
    public String getMainBody() {
        return this.mainBody.getText();
    }

    @Override
    public void visit() throws NavigationException {
        URL pageURL = Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("testpages/SimplePage.html"));
        this.getBrowserHandler().visit(pageURL);
    }
}
