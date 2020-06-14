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
    private static final Logger logger = LoggerFactory.getLogger(StopwatchEnhancer.class);

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
            logger.debug(
                    "Method invocation {}.{}() started",
                    Proxies.getUnproxiedClass(target),
                    originalMethod.getName()
            );
        } else {
            logger.debug("Stopwatch {} started", this.stopwatchName);
        }

        this.startTime = Instant.now();
    }

    @Override
    public void applyAfter(Object target, Method originalMethod, Method overriddenMethod, Object... args) {
        Instant endTime = Instant.now();
        long stopwatchDuration = Duration.between(this.startTime, endTime).toMillis();
        if (this.stopwatchName.equals("")) {
            logger.debug(
                    "Method invocation {}.{}() ended",
                    Proxies.getUnproxiedClass(target),
                    originalMethod.getName()
            );
            logger.info(
                    "Method invocation {}.{}() lasted {} ms",
                    Proxies.getUnproxiedClass(target),
                    originalMethod.getName(),
                    stopwatchDuration
            );
        } else {
            logger.debug("Stopwatch {} ended", this.stopwatchName);
            logger.info(
                    "Stopwatch {} lasted {} ms",
                    this.stopwatchName,
                    stopwatchDuration
            );
        }
    }
}
