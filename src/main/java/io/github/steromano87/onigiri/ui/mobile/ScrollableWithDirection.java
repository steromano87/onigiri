package io.github.steromano87.onigiri.ui.mobile;

import io.github.steromano87.onigiri.ui.Direction;
import io.github.steromano87.onigiri.ui.Scrollable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public interface ScrollableWithDirection extends Scrollable {
    void scrollTo(WebElement target, Direction direction) throws NoSuchElementException;

    void scrollTo(By targetBy, Direction direction) throws NoSuchElementException;
}
