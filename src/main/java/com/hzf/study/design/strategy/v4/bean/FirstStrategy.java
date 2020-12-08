package com.hzf.study.design.strategy.v4.bean;

import com.hzf.study.design.strategy.v4.Strategy;
import org.springframework.stereotype.Component;

/**
 * @Author zhuofan.han
 * @Date 2020/12/7 11:22
 */

/**
 * 策略抽象角色
 */
//@Component
public class FirstStrategy implements Strategy {
    @Override
    public void strategyMethod() {
        System.out.println("执行了第一个策略");
    }
}
