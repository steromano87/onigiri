package io.github.steromano87.onigiri.test.junit.web;

import io.github.steromano87.onigiri.test.pages.PageWithList;
import io.github.steromano87.onigiri.test.pages.PageWithSection;
import io.github.steromano87.onigiri.test.pages.PageWithSectionList;
import io.github.steromano87.onigiri.test.pages.SimplePage;
import org.junit.jupiter.api.*;


class WebPageTests extends BaseWebTest {
    @Test
    @DisplayName("Simple Web Page")
    void simpleWebPageTest() {
        SimplePage page = this.builder.build(SimplePage.class);
        page.visit();

        Assertions.assertEquals("Page title", page.getTitle());
        Assertions.assertEquals("This is the main body", page.getMainBody());
    }

    @Test
    @DisplayName("Page with list")
    void pageWithListsTest() {
        PageWithList page = this.builder.build(PageWithList.class);
        page.visit();

        Assertions.assertEquals(3, page.getItemsCount());
    }

    @Test
    @DisplayName("Page with a single section")
    void pageWithSectionTest() {
        PageWithSection page = this.builder.build(PageWithSection.class);
        page.visit();

        Assertions.assertEquals("First paragraph", page.getFirstItem());
        Assertions.assertEquals("Second paragraph", page.getSecondItem());

        Assertions.assertEquals("First paragraph", page.getSection().getFirstItem());
        Assertions.assertEquals("Second paragraph", page.getSection().getSecondItem());
    }

    @Test
    @DisplayName("Page with a list of sections")
    void pageWithSectionListTest() {
        PageWithSectionList page = this.builder.build(PageWithSectionList.class);
        page.visit();

        Assertions.assertEquals(2, page.getSections().size());

        Assertions.assertEquals(
                "First container - First paragraph",
                page.getSections().get(0).getFirstItem()
        );
        Assertions.assertEquals(
                "First container - Second paragraph",
                page.getSections().get(0).getSecondItem()
        );

        Assertions.assertEquals(
                "Second container - First paragraph",
                page.getSections().get(1).getFirstItem()
        );
        Assertions.assertEquals(
                "Second container - Second paragraph",
                page.getSections().get(1).getSecondItem()
        );
    }
}
