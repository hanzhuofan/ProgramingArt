package com.hzf.study.program.thread.volatileABA;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhuofan.han
 * @date 2022/1/12
 */
public class CASDemo {
    private volatile int num;

    private static final Unsafe UNSAFE;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe)field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    private static final long VALUE_OFFSET;
    static {
        try {
            VALUE_OFFSET = UNSAFE.objectFieldOffset(CASDemo.class.getDeclaredField("num"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public void add() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 1000; i++) {
                executorService.execute(() -> {
                    for (;;) {
                        int expect = num;
                        int update = num + 1;
                        // cas的返回值是boolean，可以点到源码里看一下，cas的源码在jdk，不会找的可以百度
                        if (UNSAFE.compareAndSwapInt(this, VALUE_OFFSET, expect, update)) {
                            break;
                        }
                    }
                });
            }
            System.out.println("num的值:" + num);
        } finally {
            executorService.shutdown();
        }
    }

    public static void main(String[] args) {
        CASDemo demo = new CASDemo();
        demo.add();
    }
}
