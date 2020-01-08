package com.github.steromano87.onigiri.test.pages;

import com.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import com.github.steromano87.onigiri.enhancers.syncing.Synced;
import com.github.steromano87.onigiri.ui.web.NavigationException;
import com.github.steromano87.onigiri.ui.web.WebSection;
import com.github.steromano87.onigiri.ui.web.Framed;
import com.github.steromano87.onigiri.ui.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;
import java.util.Objects;

public class PageWithSingleFrameSectioned extends WebPage {
    @RequiredForSync
    @FindBy(css = "h1")
    private WebElement title;

    @FindBy(css = "body")
    @Framed(@FindBy(css = "iframe"))
    private FramedSection framedSection;

    @Synced
    public String getFramedContent() {
        return this.framedSection.getFramedContent();
    }

    @Synced
    public FramedSection getFramedSection() {
        return this.framedSection;
    }

    @Override
    public void visit() throws NavigationException {
        URL pageURL = Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("testpages/PageWithSingleFrame.html"));
        this.getBrowserHandler().visit(pageURL);
    }

    public static class FramedSection extends WebSection {
        @FindBy(css = "#main-body")
        private WebElement framedContent;

        public String getFramedContent() {
            return this.framedContent.getText();
        }
    }
}
