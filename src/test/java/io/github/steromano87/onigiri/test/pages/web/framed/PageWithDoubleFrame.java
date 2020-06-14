package io.github.steromano87.onigiri.test.pages.web.framed;

import io.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import io.github.steromano87.onigiri.enhancers.syncing.Synced;
import io.github.steromano87.onigiri.ui.web.Framed;
import io.github.steromano87.onigiri.ui.web.PageUrl;
import io.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@PageUrl("/testpages/framed/PageWithDoubleFrame.html")
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
