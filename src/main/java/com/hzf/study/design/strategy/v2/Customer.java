package com.hzf.study.design.strategy.v2;

import com.hzf.study.design.strategy.v2.bean.FirstStrategy;
import com.hzf.study.design.strategy.v2.bean.SecondStrategy;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 11:27
 */

/**
 * 客户端调用策略
 */
public class Customer {
    public static void main(String[] args) {
        Strategy strategy = Context.getInstance("firstStrategy");
        strategy.strategyMethod();

        strategy = Context.getInstance("secondStrategy");
        strategy.strategyMethod();
    }
}
