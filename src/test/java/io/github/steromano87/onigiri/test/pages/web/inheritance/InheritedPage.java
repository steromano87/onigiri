package io.github.steromano87.onigiri.test.pages.web.inheritance;

import io.github.steromano87.onigiri.enhancers.syncing.Synced;
import io.github.steromano87.onigiri.ui.web.PageUrl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("/testpages/inheritance/InheritedPage.html")
public class InheritedPage extends RootPage {
    @FindBy(css = "#main-body")
    private WebElement mainBody;

    @Synced
    public String getMainBody() {
        return this.mainBody.getText();
    }
}
