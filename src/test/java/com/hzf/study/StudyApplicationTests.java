package com.hzf.study;

import com.hzf.study.design.strategy.v3.Context;
import com.hzf.study.design.strategy.v3.Strategy;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudyApplicationTests {
    @Autowired
    Context context;

    @Test
    public void testStrategyV3() {
        Strategy strategy = context.getInstance("0");
        strategy.strategyMethod();

        strategy = context.getInstance("secondStrategy");
        strategy.strategyMethod();
    }

}
