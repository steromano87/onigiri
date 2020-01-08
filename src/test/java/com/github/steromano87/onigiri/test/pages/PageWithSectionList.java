package com.github.steromano87.onigiri.test.pages;

import com.github.steromano87.onigiri.ui.web.NavigationException;
import com.github.steromano87.onigiri.ui.web.WebSection;
import com.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class PageWithSectionList extends WebPage {
    @FindBy(css = ".container")
    private List<PageSection> sections;

    public List<PageSection> getSections() {
        return this.sections;
    }

    @Override
    public void visit() throws NavigationException {
        URL pageURL = Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("testpages/PageWithSectionList.html"));
        this.getBrowserHandler().visit(pageURL);
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
