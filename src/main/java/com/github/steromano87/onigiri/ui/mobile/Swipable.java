package com.github.steromano87.onigiri.ui.mobile;

import com.github.steromano87.onigiri.ui.Direction;

/**
 * Interface implemented by WebElements that can perform a swipe on themselves
 */
public interface Swipable {
    void swipe(Direction direction);

    void swipe(double xPercStart, double xPercEnd, double yPercStart, double yPercEnd);
}
