package io.github.steromano87.onigiri.test.pages;

import io.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import io.github.steromano87.onigiri.enhancers.syncing.Status;
import io.github.steromano87.onigiri.enhancers.syncing.SyncCondition;
import io.github.steromano87.onigiri.enhancers.syncing.Synced;
import io.github.steromano87.onigiri.ui.web.ExtendedWebElement;
import io.github.steromano87.onigiri.ui.web.PageUrl;
import io.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("https://www.wikipedia.org")
public class WikipediaHome extends WebPage {
    @RequiredForSync
    @SyncCondition(Status.VISIBLE)
    @FindBy(id = "searchInput")
    private ExtendedWebElement searchBox;

    @RequiredForSync
    @SyncCondition(Status.CLICKABLE)
    @FindBy(css = ".pure-button")
    private WebElement searchButton;

    @Synced
    public void search(String searchString) {
        this.searchBox.sendKeys(searchString);
        this.searchButton.click();
    }
}
