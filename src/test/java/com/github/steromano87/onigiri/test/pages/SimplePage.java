package com.github.steromano87.onigiri.test.pages;

import com.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import com.github.steromano87.onigiri.enhancers.syncing.Synced;
import com.github.steromano87.onigiri.ui.web.PageUrl;
import com.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@PageUrl("/testpages/SimplePage.html")
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
}
