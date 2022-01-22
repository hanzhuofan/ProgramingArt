package com.hzf.study.program.thread.printABC;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhuofan.han
 * @date 2022/1/11
 */
public class PrintABCUsingLockCondition {
    private int times;

    private int state;

    private static int lockTimes = 0;

    private static int threadSize = 3;

    private static Lock lock = new ReentrantLock();

    private static List<Condition> conditions = new ArrayList<>(threadSize);

    static {
        for (int i = 0; i < threadSize; i++) {
            conditions.add(lock.newCondition());
        }
    }

    public PrintABCUsingLockCondition(int times) {
        this.times = times;
    }

    @SneakyThrows
    public static void main(String[] args) {
        PrintABCUsingLockCondition print = new PrintABCUsingLockCondition(10);
        CountDownLatch readyLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            int finalI = i;
            new Thread(() -> {
                print.printLetter(finalI, conditions.get(finalI), conditions.get(finalI == threadSize - 1 ? 0 : finalI + 1), readyLatch);
            }, "Thread-" + (finalI + 1)).start();
        }
        readyLatch.await();
        System.out.println("锁竞争次数:" + lockTimes);
    }

    private void printLetter(int targetState, Condition current, Condition next, CountDownLatch readyLatch) {
        while (true) {
            try {
                lock.lock();
                lockTimes++;
                if (state % threadSize != targetState) {
//                    System.out.println(Thread.currentThread().getName() + ": wait " + state);
                    current.await();
                }
                state++;
                if (state > times) {
                    readyLatch.countDown();
                    break;
                }
                System.out.println(Thread.currentThread().getName() + ":" + state);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                next.signal();
                lock.unlock();
            }
        }
    }
}
