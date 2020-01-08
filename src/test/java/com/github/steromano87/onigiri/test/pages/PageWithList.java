package com.github.steromano87.onigiri.test.pages;

import com.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import com.github.steromano87.onigiri.enhancers.syncing.Synced;
import com.github.steromano87.onigiri.ui.web.NavigationException;
import com.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;
import java.util.List;
import java.util.Objects;

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

    @Override
    public void visit() throws NavigationException {
        URL pageURL = Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("testpages/PageWithList.html"));
        this.getBrowserHandler().visit(pageURL);
    }
}
