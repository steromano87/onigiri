package io.github.steromano87.onigiri.enhancers.timing;

import io.github.steromano87.onigiri.Settings;
import io.github.steromano87.onigiri.enhancers.AfterMethodEnhancer;
import io.github.steromano87.onigiri.enhancers.AfterMethodPriority;
import io.github.steromano87.onigiri.enhancers.BeforeMethodEnhancer;
import io.github.steromano87.onigiri.enhancers.BeforeMethodPriority;
import io.github.steromano87.onigiri.ui.Page;
import io.github.steromano87.onigiri.ui.Section;
import io.github.steromano87.onigiri.utils.Proxies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

@BeforeMethodPriority(9999)
@AfterMethodPriority(9999)
public class StopwatchEnhancer implements BeforeMethodEnhancer, AfterMethodEnhancer {
    private Instant startTime;
    private String stopwatchName;
    private static Logger logger = LoggerFactory.getLogger(StopwatchEnhancer.class);

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
                    Proxies.getUnproxiedClass(target),
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
        logger.info(String.format("%s - lasted %d ms", stopwatchName, stopwatchDuration));
    }
}
