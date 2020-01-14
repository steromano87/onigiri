package io.github.steromano87.onigiri.handlers;

import org.openqa.selenium.Dimension;

/**
 * Interface that defines all the handlers that can query or interact with the viewport of the application under test.
 */
public interface HandlesViewport {
    float getDevicePixelRatio();

    boolean isHiDpi();

    Dimension getViewportSize();
}
