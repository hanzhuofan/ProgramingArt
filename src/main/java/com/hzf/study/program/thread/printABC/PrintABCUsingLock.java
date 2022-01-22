package com.hzf.study.program.thread.printABC;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhuofan.han
 * @date 2022/1/12
 */
public class PrintABCUsingLock {
    private int times; // 控制打印次数

    private int cur;

    private static int threadSize = 3; // 总共多少个线程

    private static int lockTimes = 0;

    private int state;   // 当前状态值：保证三个线程之间交替打印

    private final Lock lock = new ReentrantLock();

    public PrintABCUsingLock(int times) {
        this.times = times;
    }

    private void printLetter(CountDownLatch readyLatch, int targetNum) {
        for (;;) {
            lock.lock();
            lockTimes++;
            if (state % threadSize == targetNum) {
                state++;
                cur++;
                if (cur > times) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + ":" + cur);
            }
            lock.unlock();
        }
        readyLatch.countDown();
        lock.unlock();
    }

    @SneakyThrows
    public static void main(String[] args) {
        PrintABCUsingLock loopThread = new PrintABCUsingLock(10);
        CountDownLatch readyLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            int finalI = i;
            new Thread(() -> {
                loopThread.printLetter(readyLatch, finalI);
            }, "Thread-" + (finalI + 1)).start();
        }

        readyLatch.await();
        System.out.println("锁竞争次数:" + lockTimes);
    }
}
