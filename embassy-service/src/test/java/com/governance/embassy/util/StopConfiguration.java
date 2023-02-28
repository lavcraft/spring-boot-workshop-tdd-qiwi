package com.governance.embassy.util;

import org.springframework.boot.SpringBootConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@SpringBootConfiguration
public @interface StopConfiguration {
}
