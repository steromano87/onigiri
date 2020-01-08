package com.github.steromano87.onigiri.enhancers.syncing;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Synced {
}
