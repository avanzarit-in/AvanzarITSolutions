package com.avanzarit.apps.gst;

import java.lang.annotation.*;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Layout {
    String value() default "";
}
