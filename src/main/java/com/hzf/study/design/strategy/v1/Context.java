package com.hzf.study.design.strategy.v1;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 11:26
 */

/**
 * 策略管理角色
 */
public class Context {
    //持有一个具体的策略对象
    private final Strategy strategy;

    //构造上下文时传入一个具体对象
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    //调用该策略的方法
    public void strategyMethod() {
        strategy.strategyMethod();
    }

}
