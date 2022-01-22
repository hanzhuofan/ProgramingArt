package com.hzf.study.program.thread.printABC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author zhuofan.han
 * @date 2022/1/12
 */
public class PrintABCUsingSemaphore {
    private int times;

    private int state;

    private static int threadSize = 3;

    private static List<Semaphore> semaphores = new ArrayList<>();

    static {
        for (int i = 0; i < threadSize; i++) {
            semaphores.add(new Semaphore(0));
        }
        semaphores.get(0).release();
    }

    public PrintABCUsingSemaphore(int times) {
        this.times = times;
    }

    public static void main(String[] args) {
        PrintABCUsingSemaphore printer = new PrintABCUsingSemaphore(10);
        for (int i = 0; i < threadSize; i++) {
            int finalI = i;
            new Thread(() -> {
                printer.print(semaphores.get(finalI), semaphores.get(finalI == threadSize - 1 ? 0 : finalI + 1));
            }, "Thread-" + (finalI + 1)).start();
        }
    }

    private void print(Semaphore current, Semaphore next) {
        for (;;) {
            try {
                current.acquire();  // A获取信号执行，A信号量减1，当A为0时将无法继续获得该信号量
                state++;
                if (state > times) {
                    next.release();
                    break;
                }
                System.out.println(Thread.currentThread().getName() + ":" + state);
                next.release();     // B释放信号，B信号量加1（初始为0），此时可以获取B信号量
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
