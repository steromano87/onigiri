package io.github.steromano87.onigiri.test.pages.web.inheritance;

import io.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import io.github.steromano87.onigiri.enhancers.syncing.Synced;
import io.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RootPage extends WebPage {
    @RequiredForSync
    @FindBy(css = "h1")
    private WebElement title;

    @Synced
    public String getTitle() {
        return this.title.getText();
    }
}
