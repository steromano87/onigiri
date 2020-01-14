package io.github.steromano87.onigiri.test.pages;

import io.github.steromano87.onigiri.enhancers.syncing.RequiredForSync;
import io.github.steromano87.onigiri.enhancers.syncing.Synced;
import io.github.steromano87.onigiri.ui.web.*;
import io.github.steromano87.onigiri.ui.web.Framed;
import io.github.steromano87.onigiri.ui.web.PageUrl;
import io.github.steromano87.onigiri.ui.web.WebPage;
import io.github.steromano87.onigiri.ui.web.WebSection;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@PageUrl("/testpages/PageWithSingleFrame.html")
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

    public static class FramedSection extends WebSection {
        @FindBy(css = "#main-body")
        private WebElement framedContent;

        public String getFramedContent() {
            return this.framedContent.getText();
        }
    }
}
