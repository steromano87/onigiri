package com.github.steromano87.onigiri.handlers;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.ScreenOrientation;

public class DeviceHandler implements HandlesViewport, HasPlatformInformation {
    private MobileDriver driver;

    public DeviceHandler(MobileDriver driver) {
        this.driver = driver;
    }

    @Override
    public float getDevicePixelRatio() {
        return 1;
    }

    @Override
    public boolean isHiDpi() {
        return false;
    }

    @Override
    public Dimension getViewportSize() {
        return null;
    }

    @Override
    public Platform getPlatform() {
        return null;
    }

    public ScreenOrientation getScreenOrientation() {
        return null;
    }

    public String getDeviceName() {
        return null;
    }
}
