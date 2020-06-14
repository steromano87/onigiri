package io.github.steromano87.onigiri.test.junit.web;

import io.github.steromano87.onigiri.test.pages.web.inheritance.DoubleInheritedPage;
import io.github.steromano87.onigiri.test.pages.web.inheritance.InheritedPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PageInheritanceTests extends BaseWebTest {
    @Test
    @DisplayName("Page class inherited from another page class (with some elements defined in the parent class")
    void simpleInheritanceTest() {
        InheritedPage page = this.builder.build(InheritedPage.class);
        page.visit();

        Assertions.assertEquals("Page title", page.getTitle());
        Assertions.assertEquals("This is the main body", page.getMainBody());
    }

    @Test
    @DisplayName("Page class inherited from an already inherited page class")
    void doubleInheritanceTest() {
        DoubleInheritedPage page = this.builder.build(DoubleInheritedPage.class);
        page.visit();

        Assertions.assertEquals("Page title", page.getTitle());
        Assertions.assertEquals("This is the main body", page.getMainBody());
        Assertions.assertEquals("This is the inherited body", page.getInheritedText());
    }
}
