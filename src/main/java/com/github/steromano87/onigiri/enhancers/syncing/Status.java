package com.github.steromano87.onigiri.enhancers.syncing;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;


public enum Status {
    VISIBLE {
        @Override
        public ExpectedCondition<?> buildExpectedCondition(By locator) {
            return ExpectedConditions.visibilityOfElementLocated(locator);
        }

        @Override
        public String getDefaultFailureMessageTemplate() {
            return "'%s' is not visible";
        }
    },
    INVISIBLE {
        @Override
        public ExpectedCondition<?> buildExpectedCondition(By locator) {
            return ExpectedConditions.invisibilityOfElementLocated(locator);
        }

        @Override
        public String getDefaultFailureMessageTemplate() {
            return "'%s' is visible, but it should be invisible";
        }
    },
    ABSENT {
        @Override
        public ExpectedCondition<?> buildExpectedCondition(By locator) {
            return (ExpectedCondition<Boolean>) input ->
                    Objects.requireNonNull(input).findElements(locator).size() == 0;
        }

        @Override
        public String getDefaultFailureMessageTemplate() {
            return "'%s' is present on page, but it should not be present";
        }
    },
    CLICKABLE {
        @Override
        public ExpectedCondition<?> buildExpectedCondition(By locator) {
            return ExpectedConditions.elementToBeClickable(locator);
        }

        @Override
        public String getDefaultFailureMessageTemplate() {
            return "'%s' is not clickable";
        }
    };

    public abstract ExpectedCondition<?> buildExpectedCondition(By locator);

    public abstract String getDefaultFailureMessageTemplate();
}
