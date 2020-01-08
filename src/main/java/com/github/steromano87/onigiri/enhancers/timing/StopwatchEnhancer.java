package com.github.steromano87.onigiri.enhancers.timing;

import com.github.steromano87.onigiri.Settings;
import com.github.steromano87.onigiri.enhancers.AfterMethodEnhancer;
import com.github.steromano87.onigiri.enhancers.AfterMethodPriority;
import com.github.steromano87.onigiri.enhancers.BeforeMethodEnhancer;
import com.github.steromano87.onigiri.enhancers.BeforeMethodPriority;
import com.github.steromano87.onigiri.ui.Page;
import com.github.steromano87.onigiri.ui.Section;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

@BeforeMethodPriority(1)
@AfterMethodPriority(100)
public class StopwatchEnhancer implements BeforeMethodEnhancer, AfterMethodEnhancer {
    private Instant startTime;
    private String stopwatchName;
    private static Logger logger = LogManager.getLogger(StopwatchEnhancer.class);
    private static final Level STOPWATCH = Level.forName("STOPWATCH", 380);

    @Override
    public boolean isApplicableBefore(Object target, Method originalMethod, Method overriddenMethod, Object... args) {
        return (Page.class.isAssignableFrom(target.getClass())
                || Section.class.isAssignableFrom(target.getClass()))
                && originalMethod.isAnnotationPresent(Stopwatch.class)
                && Settings.getInstance().getBoolean(Settings.STOPWATCH_ENABLED);
    }

    @Override
    public boolean isApplicableAfter(Object target, Method originalMethod, Method overriddenMethod, Object... args) {
        return isApplicableBefore(target, originalMethod, overriddenMethod, args);
    }

    @Override
    public void applyBefore(Object target, Method originalMethod, Method overriddenMethod, Object... args) {
        this.stopwatchName = originalMethod.getAnnotation(Stopwatch.class).value();
        if (this.stopwatchName.equals("")) {
            this.stopwatchName = String.format(
                    "Method invocation %s.%s()",
                    this.getVanillaClassName(target.getClass()),
                    originalMethod.getName()
            );
        }

        logger.debug(String.format("Stopwatch %s started", this.stopwatchName));
        this.startTime = Instant.now();
    }

    @Override
    public void applyAfter(Object target, Method originalMethod, Method overriddenMethod, Object... args) {
        logger.debug(String.format("%s stopwatch ended", this.stopwatchName));
        Instant endTime = Instant.now();

        this.logStopwatchDuration(stopwatchName, Duration.between(this.startTime, endTime).toMillis());
    }

    private void logStopwatchDuration(String stopwatchName, long stopwatchDuration) {
        logger.log(STOPWATCH, String.format("%s - lasted %d ms", stopwatchName, stopwatchDuration));
    }
}
