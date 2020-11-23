package com.hzf.study.design.strategy.bean;

import com.hzf.study.design.strategy.Strategy;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 11:26
 */
public class OperationMultiply implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 * num2;
    }
}
