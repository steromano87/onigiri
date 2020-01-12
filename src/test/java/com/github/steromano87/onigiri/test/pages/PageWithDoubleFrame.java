package com.github.steromano87.onigiri.test.pages;

import com.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import com.github.steromano87.onigiri.enhancers.syncing.Synced;
import com.github.steromano87.onigiri.ui.web.Framed;
import com.github.steromano87.onigiri.ui.web.PageUrl;
import com.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@PageUrl("/testpages/PageWithDoubleFrame.html")
public class PageWithDoubleFrame extends WebPage {
    @RequiredForSync
    @FindBy(css = "h1")
    private WebElement title;

    @FindBy(css = "#main-body")
    @Framed({@FindBy(css = "#outer-frame"), @FindBy(css = "iframe")})
    private WebElement framedContent;

    @Synced
    public String getFramedContent() {
        return this.framedContent.getText();
    }
}
