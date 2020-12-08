package com.hzf.study.design.strategy.v3.annotation;

import java.lang.annotation.*;

/**
 * @Author zhuofan.han
 * @Date 2020/12/7 14:37
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerType {
    String value();
}
