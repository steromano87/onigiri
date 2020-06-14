package io.github.steromano87.onigiri.test.pages.web.inheritance;

import io.github.steromano87.onigiri.enhancers.syncing.Synced;
import io.github.steromano87.onigiri.ui.web.PageUrl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("/testpages/inheritance/InheritedPage.html")
public class DoubleInheritedPage extends InheritedPage {
    @FindBy(id = "inherited")
    private WebElement inheritedText;

    @Synced
    public String getInheritedText() {
        return this.inheritedText.getText();
    }
}
