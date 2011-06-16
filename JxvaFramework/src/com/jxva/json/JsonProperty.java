package com.jxva.json;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Target(value = ElementType.METHOD)
public @interface JsonProperty {

	String value() default "";

	boolean ignore() default false;

	boolean ignoreIfNull() default false;
}
