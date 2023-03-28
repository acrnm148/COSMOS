package com.cosmos.back.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCached {

    /**
     * main cache key name
     */
    String key();

    /**
     * expire time
     */
    int expire(); //default 1800;

    /**
     * force proceed method
     */
    boolean replace() default false;
}