package io.github.steromano87.onigiri.test.pages;

import io.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import io.github.steromano87.onigiri.enhancers.syncing.Synced;
import io.github.steromano87.onigiri.ui.web.PageUrl;
import io.github.steromano87.onigiri.ui.web.WebPage;
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
