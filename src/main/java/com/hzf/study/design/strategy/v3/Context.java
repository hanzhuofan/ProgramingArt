package com.hzf.study.design.strategy.v3;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 11:26
 */

import com.hzf.study.utils.BeanTool;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 策略管理角色
 */
@SuppressWarnings("ALL")
public class Context {
    private Map<String, Class> handlerMap;

    public Context(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public Strategy getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (Strategy) BeanTool.getBean(clazz);
    }
}
