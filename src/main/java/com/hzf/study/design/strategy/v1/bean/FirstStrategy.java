package com.hzf.study.design.strategy.v1.bean;

import com.hzf.study.design.strategy.v1.Strategy;

/**
 * @Author zhuofan.han
 * @Date 2020/12/7 11:22
 */
/**
 * 策略抽象角色
 */
public class FirstStrategy implements Strategy {
    @Override
    public void strategyMethod() {
        System.out.println("执行了第一个策略");
    }
}
