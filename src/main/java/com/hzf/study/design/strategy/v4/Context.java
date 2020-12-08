package com.hzf.study.design.strategy.v4;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 11:26
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略管理角色
 */
//@Component
public class Context implements ApplicationContextAware {
    private final Map<String, Strategy> STRATEGY = new HashMap<>();

    public Strategy getInstance(String type) {
        return STRATEGY.get(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Strategy> map = applicationContext.getBeansOfType(Strategy.class);
        map.forEach(STRATEGY::put);
    }
}
