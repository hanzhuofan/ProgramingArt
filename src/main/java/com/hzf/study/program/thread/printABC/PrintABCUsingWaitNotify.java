package com.hzf.study.program.thread.printABC;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhuofan.han
 * @date 2022/1/12
 */
public class PrintABCUsingWaitNotify {
    private int state;

    private int times;

    private static int threadSize = 3; // 总共多少个线程

    private static int lockTimes = 0;

    private static final Object LOCK = new Object();

    public PrintABCUsingWaitNotify(int times) {
        this.times = times;
    }

    @SneakyThrows
    public static void main(String[] args) {
        PrintABCUsingWaitNotify printABC = new PrintABCUsingWaitNotify(10);
        CountDownLatch readyLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            int finalI = i;
            new Thread(() -> {
                printABC.printLetter(readyLatch, finalI);
            }, "Thread-" + (finalI + 1)).start();
        }

        readyLatch.await();
        System.out.println("锁竞争次数:" + lockTimes);
    }

    private void printLetter(CountDownLatch readyLatch, int targetState) {
        for (;;) {
            synchronized (LOCK) {
                lockTimes++;
                while (state % 3 != targetState) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " wait");
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                state++;
                LOCK.notifyAll();
                if (state > times) {
                    readyLatch.countDown();
                    break;
                }
                System.out.println(Thread.currentThread().getName() + ":" + state);
            }
        }
    }
}
