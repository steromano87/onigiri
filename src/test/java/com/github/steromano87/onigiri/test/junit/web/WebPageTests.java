package com.github.steromano87.onigiri.test.junit.web;

import com.github.steromano87.onigiri.test.pages.*;
import com.github.steromano87.onigiri.factory.PageBuilder;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


class WebPageTests {
    private WebDriver driver;
    private PageBuilder builder;

    @BeforeEach
    void beforeHook() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            this.driver = new ChromeDriver(options);
            this.builder = new PageBuilder(this.driver);
        } catch (IllegalStateException exc) {
            Assumptions.assumeTrue(false);
        }
    }

    @AfterEach
    void afterHook() {
        this.driver.quit();
    }

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

    @Test
    @DisplayName("Page with a framed element (one frame)")
    void pageWithSingleFrameTest() {
        PageWithSingleFrame page = this.builder.build(PageWithSingleFrame.class);
        page.visit();

        Assertions.assertEquals("This is the main body", page.getFramedContent());
    }

    @Test
    @DisplayName("Page with a framed element (two nested frames)")
    void pageWithDoubleFrameTest() {
        PageWithDoubleFrame page = this.builder.build(PageWithDoubleFrame.class);
        page.visit();

        Assertions.assertEquals("This is the main body", page.getFramedContent());
    }

    @Test
    @DisplayName("Page with a framed section (one frame)")
    void pageWithSingleFrameSectionedTest() {
        PageWithSingleFrameSectioned page = this.builder.build(PageWithSingleFrameSectioned.class);
        page.visit();

        Assertions.assertEquals("This is the main body", page.getFramedContent());
        Assertions.assertEquals("This is the main body", page.getFramedSection().getFramedContent());
    }
}
