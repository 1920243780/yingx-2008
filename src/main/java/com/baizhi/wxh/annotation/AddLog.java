package com.baizhi.wxh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //定义注解用在什么位置
@Retention(RetentionPolicy.RUNTIME) //注解什么时候生效
public @interface AddLog {
    String name() default "";

    String value();
}
