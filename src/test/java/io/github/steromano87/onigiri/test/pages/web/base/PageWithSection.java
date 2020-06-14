package io.github.steromano87.onigiri.test.pages.web.base;

import io.github.steromano87.onigiri.ui.web.PageUrl;
import io.github.steromano87.onigiri.ui.web.WebPage;
import io.github.steromano87.onigiri.ui.web.WebSection;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@PageUrl("/testpages/base/PageWithSection.html")
public class PageWithSection extends WebPage {
    @FindBy(css = "#container")
    private PageSection section;

    public PageSection getSection() {
        return this.section;
    }

    public String getFirstItem() {
        return this.section.getFirstItem();
    }

    public String getSecondItem() {
        return this.section.getSecondItem();
    }


    public static class PageSection extends WebSection {
        @FindBy(css = "p.first")
        private WebElement firstItem;

        @FindBy(css = "p.second")
        private WebElement secondItem;

        public String getFirstItem() {
            return this.firstItem.getText();
        }

        public String getSecondItem() {
            return this.secondItem.getText();
        }
    }
}
