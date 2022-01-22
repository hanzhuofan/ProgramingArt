package com.hzf.study.program.thread.printABC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author zhuofan.han
 * @date 2022/1/11
 */
public class PrintABCUsingLockSupport {
    private static List<Thread> threadList = new ArrayList<>(); // 存放线程的集合

    private static int threadSize = 3; // 总共多少个线程

    private static int threadIndex = 0; // 当前线程下标

    private static int maxValue = 10; // 需要输出的数的最大值

    private static int curValue = 1; // 数的当前值

    public static void main(String[] args) {
        for (int i = 1; i <= threadSize; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    // 阻塞当前线程
                    LockSupport.park();
                    if (curValue <= maxValue) {
                        System.out.println(Thread.currentThread().getName() + ":" + curValue++);
                    } else {
                        // 唤起下一个线程
                        LockSupport.unpark(threadList.get(++threadIndex % threadList.size()));
                        break;
                    }
                    // 唤起下一个线程
                    LockSupport.unpark(threadList.get(++threadIndex % threadList.size()));
                }
            });
            thread.setName(String.format("Thread-%d", i));
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
            thread.start();
        }
        LockSupport.unpark(threadList.get(0));
    }
}
