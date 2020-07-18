package com.hzf.study.program;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hanzhuofan
 * @date 2020/7/18
 * 描述 Java中的ThreadLocal类允许我们创建只能被同一个线程读写的变量。
 * 因此，如果一段代码含有一个ThreadLocal变量的引用，即使两个线程同时执行这段代码，
 * 它们也无法访问到对方的ThreadLocal变量。
 */
public class ThreadLocalTest {
    private final ThreadLocal<String> tl = new ThreadLocal<>();

    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    private static final ExecutorService SERVICE = new ThreadPoolExecutor(5, 10,
            1L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20), new NamedThreadFactory("test"));

    public static void main(String[] args) {
        ThreadLocalTest tlt = new ThreadLocalTest();
        for (int i = 1; i < 6; i++) {
            int finalI = i;
            SERVICE.execute(() -> {
                tlt.setContext(Thread.currentThread().getName());
                tlt.tl.set(Thread.currentThread().getName());
                System.out.println(finalI + "------>" + tlt.getContext());
                System.out.println(finalI + "--tl-->" + tlt.tl.get());
            });
        }
    }
    /**
     * 1------>test-1-thread-3
     * 1--tl-->test-1-thread-1
     * 2------>test-1-thread-2
     * 2--tl-->test-1-thread-2
     * 3------>test-1-thread-3
     * 3--tl-->test-1-thread-3
     * 5------>test-1-thread-5
     * 5--tl-->test-1-thread-5
     * 4------>test-1-thread-4
     * 4--tl-->test-1-thread-4
     */
}

class NamedThreadFactory implements ThreadFactory{

    private final AtomicInteger poolNumber = new AtomicInteger(1);

    private final ThreadGroup threadGroup;

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public  final String namePrefix;

    NamedThreadFactory(String name){
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        if (null==name || "".equals(name.trim())){
            name = "pool";
        }
        namePrefix = name +"-"+
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(threadGroup, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
