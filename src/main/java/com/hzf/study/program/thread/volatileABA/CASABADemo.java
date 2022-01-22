package com.hzf.study.program.thread.volatileABA;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sun.misc.Unsafe;

/**
 * @author zhuofan.han
 * @date 2022/1/12
 */
public class CASABADemo {
    private volatile int num;

    private static final Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    private static final long valueOffset;
    static {
        try {
            valueOffset = unsafe.objectFieldOffset(CASABADemo.class.getDeclaredField("num"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public void add() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 100000; i++) {
                executorService.execute(() -> {
                    for (;;) {
                        int expect = num;
                        int update = num + 1;
                        // cas的返回值是boolean，可以点到源码里看一下，cas的源码在jdk，不会找的可以百度
                        if (unsafe.compareAndSwapInt(this, valueOffset, expect, update)) {
                            break;
                        }
                    }
                    for (;;) {
                        int expect = num;
                        int update = num - 1;
                        // cas的返回值是boolean，可以点到源码里看一下，cas的源码在jdk，不会找的可以百度
                        if (unsafe.compareAndSwapInt(this, valueOffset, expect, update)) {
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
        CASABADemo demo = new CASABADemo();
        demo.add();
    }
}
