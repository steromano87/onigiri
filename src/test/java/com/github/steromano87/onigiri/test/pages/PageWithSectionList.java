package com.github.steromano87.onigiri.test.pages;

import com.github.steromano87.onigiri.ui.web.NavigationException;
import com.github.steromano87.onigiri.ui.web.PageUrl;
import com.github.steromano87.onigiri.ui.web.WebSection;
import com.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;
import java.util.List;
import java.util.Objects;

@PageUrl("/testpages/PageWithSectionList.html")
public class PageWithSectionList extends WebPage {
    @FindBy(css = ".container")
    private List<PageSection> sections;

    public List<PageSection> getSections() {
        return this.sections;
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
