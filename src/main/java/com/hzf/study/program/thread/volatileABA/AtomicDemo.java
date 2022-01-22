package com.hzf.study.program.thread.volatileABA;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author zhuofan.han
 * @date 2022/1/12
 */
public class AtomicDemo {
    private volatile AtomicInteger num = new AtomicInteger();

    private static final AtomicStampedReference<Integer> REFERENCE = new AtomicStampedReference<>(10086, 1);

    public void add() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 100000; i++) {
                executorService.execute(() -> {
                    num.getAndIncrement();
                    num.getAndDecrement();
                });
            }
            System.out.println("num的值:" + num);

            executorService.execute(() -> {
                int stamp = REFERENCE.getStamp();
                System.out.println(Thread.currentThread().getName() + " 标记:" + stamp);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean c = REFERENCE.compareAndSet(REFERENCE.getReference(), 10087, REFERENCE.getStamp(),
                    REFERENCE.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + " 标记:" + REFERENCE.getStamp() + " boolean:" + c);
                boolean b = REFERENCE.compareAndSet(REFERENCE.getReference(), 10086, REFERENCE.getStamp(),
                    REFERENCE.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + " 标记:" + REFERENCE.getStamp() + " boolean:" + b);

            });

            executorService.execute(() -> {
                int stamp = REFERENCE.getStamp();
                System.out.println(Thread.currentThread().getName() + " 标记:" + stamp);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean b = REFERENCE.compareAndSet(REFERENCE.getReference(), 10010, stamp, stamp + 1);
                System.out
                    .println(Thread.currentThread().getName() + " value：" + REFERENCE.getReference() + " boolean:" + b);

            });
        } finally {
            executorService.shutdown();
        }
    }

    public static void main(String[] args) {
        AtomicDemo demo = new AtomicDemo();
        demo.add();
    }
}
