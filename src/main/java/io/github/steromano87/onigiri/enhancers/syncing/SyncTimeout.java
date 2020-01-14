package io.github.steromano87.onigiri.enhancers.syncing;

import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SyncTimeout {
    int value();

    ChronoUnit unit() default ChronoUnit.SECONDS;
}
