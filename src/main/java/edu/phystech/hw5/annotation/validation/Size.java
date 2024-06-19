package edu.phystech.hw5.annotation.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author kzlv4natoly
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {
    int min() default 1;
    int max();
    String message() default "";
}
