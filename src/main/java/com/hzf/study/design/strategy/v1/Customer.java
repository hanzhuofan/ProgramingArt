package com.hzf.study.design.strategy.v1;

import com.hzf.study.design.strategy.v1.bean.FirstStrategy;
import com.hzf.study.design.strategy.v1.bean.SecondStrategy;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 11:27
 */
/**
 * 客户端调用策略
 */
public class Customer {
    public static void main(String[] args) {
        Strategy firstStrategy = new FirstStrategy();
        Context context = new Context(firstStrategy);
        context.strategyMethod();

        Strategy secondStrategy = new SecondStrategy();
        context = new Context(secondStrategy);
        context.strategyMethod();
    }
}
