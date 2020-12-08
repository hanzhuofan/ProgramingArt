package com.hzf.study.design.strategy.v2;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 11:26
 */

import com.hzf.study.design.strategy.v2.bean.FirstStrategy;
import com.hzf.study.design.strategy.v2.bean.SecondStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略管理角色
 */
public class Context {
    private static final Map<String, Strategy> STRATEGY = new HashMap<>();

    static {
        STRATEGY.put("firstStrategy", new FirstStrategy());
        STRATEGY.put("secondStrategy", new SecondStrategy());
    }

    public static Strategy getInstance(String type) {
        return STRATEGY.get(type);
    }

}
