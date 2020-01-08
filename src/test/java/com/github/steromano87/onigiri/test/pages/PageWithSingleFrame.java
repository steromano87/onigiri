package com.github.steromano87.onigiri.test.pages;

import com.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import com.github.steromano87.onigiri.enhancers.syncing.Synced;
import com.github.steromano87.onigiri.ui.web.NavigationException;
import com.github.steromano87.onigiri.ui.web.Framed;
import com.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;
import java.util.Objects;

public class PageWithSingleFrame extends WebPage {
    @RequiredForSync
    @FindBy(css = "h1")
    private WebElement title;

    @FindBy(css = "#main-body")
    @Framed(@FindBy(css = "iframe"))
    private WebElement framedContent;

    @Synced
    public String getFramedContent() {
        return this.framedContent.getText();
    }

    @Override
    public void visit() throws NavigationException {
        URL pageURL = Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("testpages/PageWithSingleFrame.html"));
        this.getBrowserHandler().visit(pageURL);
    }
}
