package com.hzf.study.design.strategy;

import com.hzf.study.design.strategy.bean.OperationAdd;
import com.hzf.study.design.strategy.bean.OperationMultiply;
import com.hzf.study.design.strategy.bean.OperationSubtract;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 11:27
 */
public class StrategyPatternDemo {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationSubtract());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationMultiply());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
    }
}
