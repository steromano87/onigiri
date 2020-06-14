package io.github.steromano87.onigiri.test.pages.web.base;

import io.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import io.github.steromano87.onigiri.enhancers.syncing.Synced;
import io.github.steromano87.onigiri.ui.web.PageUrl;
import io.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageUrl("/testpages/base/PageWithList.html")
public class PageWithList extends WebPage {
    @RequiredForSync
    @FindBy(css = "h1")
    private WebElement title;

    @FindBy(css = "ul > li")
    private List<WebElement> items;

    @Synced
    public int getItemsCount() {
        return this.items.size();
    }
}
