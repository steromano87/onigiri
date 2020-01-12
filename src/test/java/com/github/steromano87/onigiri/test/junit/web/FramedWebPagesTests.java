package com.github.steromano87.onigiri.test.junit.web;

import com.github.steromano87.onigiri.test.pages.PageWithDoubleFrame;
import com.github.steromano87.onigiri.test.pages.PageWithSingleFrame;
import com.github.steromano87.onigiri.test.pages.PageWithSingleFrameSectioned;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FramedWebPagesTests extends BaseWebTest {
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
