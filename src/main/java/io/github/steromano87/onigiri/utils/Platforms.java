package io.github.steromano87.onigiri.utils;

import org.openqa.selenium.Platform;

public class Platforms {
    private Platforms() {}

    public static boolean isMobile(Platform platform) {
        return platform.is(Platform.ANDROID) || platform.is(Platform.IOS);
    }
}
