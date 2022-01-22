package com.hzf.study.program.thread.volatileABA;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhuofan.han
 * @date 2022/1/12
 */
public class Demo {
    private volatile int num;

    public void add() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 1000; i++) {
                executorService.execute(() -> num++);
            }
            System.out.println("num的值:" + num);
        } finally {
            executorService.shutdown();
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.add();
    }
}
