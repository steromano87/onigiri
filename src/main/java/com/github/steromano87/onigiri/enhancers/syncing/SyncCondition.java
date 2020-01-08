package com.github.steromano87.onigiri.enhancers.syncing;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SyncCondition {
    Status value();
}
