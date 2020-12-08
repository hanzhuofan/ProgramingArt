package com.hzf.study.design.strategy.v3.bean;

import com.hzf.study.design.strategy.v3.Strategy;
import com.hzf.study.design.strategy.v3.annotation.HandlerType;
import org.springframework.stereotype.Component; /**
 * @Author zhuofan.han
 * @Date 2020/12/7 11:22
 */

/**
 * 策略抽象角色
 */
@Component
@HandlerType("secondStrategy")
public class SecondStrategy implements Strategy {
    @Override
    public void strategyMethod() {
        System.out.println("执行了第二个策略");
    }
}
