package com.hzf.study;

import com.hzf.study.controller.GreetingController;
import com.hzf.study.service.ActivitiService;
import com.hzf.study.utils.DateHelper;
import com.hzf.study.utils.RuntimeUtils;
import com.sun.management.OperatingSystemMXBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author zhuofan.han
 * @Date 2020/11/11 17:14
 */
@Slf4j
public class Play {
    public static void main(String[] args) throws Exception {
        GreetingController greetingController = new GreetingController();
        Class<? extends GreetingController> aClass = greetingController.getClass();

        ActivitiService activitiService = new ActivitiService();
        System.out.println(activitiService);

        Method setGreetingService = aClass.getDeclaredMethod("setGreetingService", ActivitiService.class);
        setGreetingService.invoke(greetingController, activitiService);

        Double d = 10.0;
        System.out.println(Math.floor(d) == d);
        if (Math.floor(d) == d) {
            System.out.println(d.intValue());
        }
        System.out.println(d.intValue());

        List<Integer> tmp = new ArrayList<Integer>() {
            {
                add(9);
                add(6);
                add(3);
                add(8);
                add(5);
                add(2);
                add(7);
                add(4);
                add(1);
                add(0);
            }
        };

        tmp.sort(Comparator.comparingInt(a -> a));
        System.out.println(tmp);
        tmp.sort((a, b) -> b - a);
        System.out.println(tmp);

        long now = System.currentTimeMillis();
        long epochMilli = Instant.now().toEpochMilli();
        System.out.println(now);
        System.out.println(epochMilli);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();

        System.out.println(DateFormat.getInstance().format(now));
        System.out.println(DateFormat.getInstance().format(DateHelper.getAmountMonth(DateHelper.getAmountDay(now, -4), 7)));

        Set<Test> set = new HashSet<>();
        set.add(new Test(1, 2));
        set.remove(new Test(1, 3));
        set.add(new Test(1, 3));
        System.out.println(set);

        Boolean a = true;
        Boolean b = true;
        Boolean c = false;
        System.out.println(a.equals(b));
        System.out.println(a.equals(c));

        System.out.println("=============================");
        System.out.println(RuntimeUtils.getSystemCpuLoad());
        System.out.println(RuntimeUtils.getProcessCpuLoad());
        System.out.println(RuntimeUtils.getTotalPhysicalMemorySize());
        System.out.println(RuntimeUtils.getFreePhysicalMemorySize());
        System.out.println(RuntimeUtils.getUsedPhysicalMemorySize());

        System.out.println((7 & 1) != 0);
        System.out.println(7 & 2);
        System.out.println(7 & 4);
        System.out.println(2 & 4);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
        System.out.println(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli());
        System.out.println(System.currentTimeMillis());

        String[] split = "asda  s".split("");
        List<String> collect = Stream.of(split).collect(Collectors.toList());
        System.out.println(collect);



//        for (int i = 0; i < 4; i++) {
//            new Thread(() -> {
//                while (true) {
//                    long bac = 1000000;
//                    bac = bac >> 1;
//                }
//            }).start();
//        }
//
//        while(true) {
//            SystemInfo systemInfo = new SystemInfo();
////            GlobalMemory memory = systemInfo.getHardware().getMemory();
////            long totalByte = memory.getTotal();
////            long acaliableByte = memory.getAvailable();
////
////            log.info("内存大小 = {},内存使用率 ={}",formatByte(totalByte),new DecimalFormat("#.##%").format((totalByte-acaliableByte)*1.0/totalByte));
//
//            CentralProcessor processor = systemInfo.getHardware().getProcessor();
//            long[] prevTicks = processor.getSystemCpuLoadTicks();
//            long[][] processorCpuLoadTicks = processor.getProcessorCpuLoadTicks();
//            // Wait a second...
//            Util.sleep(1000);
//            System.out.println("cpu当前使用率1==:" + new DecimalFormat("#.##%").format(processor.getSystemCpuLoadBetweenTicks(prevTicks)));
//            System.out.println("cpu当前使用率2==:" + new DecimalFormat("#.##%").format(osmxb.getSystemCpuLoad()));
//            System.out.println("cpu当前使用率3==:" + new DecimalFormat("#.##%").format(Play.getInstance().getProcessCpu()));
//            Thread.sleep(0);
//        }

        // 获取 Java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程 ID 和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }

        for (int i = 0; i < 5; i++) {
//            new Thread(Play::await123, "Thread-" + i).start();
        }
//        new Thread(Play::await123, "Thread-1").start();
//        new Thread(Play::signal, "Thread-2").start();
//        new Thread(Play::signalAll, "Thread-signalAll").start();

        CountDownLatch readyLatch = new CountDownLatch(5);
        CountDownLatch runningLatchWait = new CountDownLatch(1);
        CountDownLatch completeLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new CountDownDemo(readyLatch,runningLatchWait,completeLatch), "Thread-" + i).start();
        }
        readyLatch.await();//等待发令
        System.out.println("发令");
        runningLatchWait.countDown();//发令
        completeLatch.await();//等所有子线程执行完
        System.out.println("主线程执行完毕");
    }

    private static final Lock lock = new ReentrantLock(true);

    private static final Condition condition = lock.newCondition();

    public static void await123() {
        System.out.println(Thread.currentThread().getName() + "开始wait");
        try {
            lock.lock();
//            condition.await();
            Thread.sleep(10);
            System.out.println("ThreadName=" + Thread.currentThread().getName() + " ok");
//            for (int i = 0; i < 5; i++) {
//                System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void signal() {
        System.out.println(Thread.currentThread().getName() + "开始wait");
        try {
            lock.lock();
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "-signal");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void signalAll() {
        System.out.println(Thread.currentThread().getName() + "开始wait");
        try {
            lock.lock();
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "-signalAll");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private static Play instance = new Play();

    private java.lang.management.OperatingSystemMXBean osMxBean;
    private ThreadMXBean threadBean;
    private long preTime = System.nanoTime();
    private long preUsedTime = 0;

    private Play() {
        osMxBean = ManagementFactory.getOperatingSystemMXBean();
        threadBean = ManagementFactory.getThreadMXBean();
    }

    public static Play getInstance() {
        return instance;
    }

    public double getProcessCpu() {
        long totalTime = 0;
        for (long id : threadBean.getAllThreadIds()) {
            totalTime += threadBean.getThreadCpuTime(id);
        }
        long curtime = System.nanoTime();
        long usedTime = totalTime - preUsedTime;
        long totalPassedTime = curtime - preTime;
        preTime = curtime;
        preUsedTime = totalTime;
        return (((double) usedTime) / totalPassedTime / osMxBean.getAvailableProcessors());
    }

    public static String formatByte(long byteNumber){
        double FORMAT = 1024.0;
        double kbNumber = byteNumber/FORMAT;
        if(kbNumber<FORMAT){
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber/FORMAT;
        if(mbNumber<FORMAT){
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber/FORMAT;
        if(gbNumber<FORMAT){
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber/FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

    @Data
    @AllArgsConstructor
    static class Test {
        private int a;
        private int b;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Test test = (Test) o;
            return a == test.a;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a);
        }
    }


    public static class CountDownDemo implements Runnable{
        private CountDownLatch readyLatch;
        private CountDownLatch runningLatchWait;
        private CountDownLatch completeLatch;

        public CountDownDemo(CountDownLatch readyLatch, CountDownLatch runningLatchWait, CountDownLatch completeLatch) {
            this.readyLatch = readyLatch;
            this.runningLatchWait = runningLatchWait;
            this.completeLatch = completeLatch;
        }

        @Override
        public void run() {
            readyLatch.countDown();
            try {
                System.out.println("线程" + Thread.currentThread().getName() + " readyLatch");
                runningLatchWait.await();
                System.out.println("线程" + Thread.currentThread().getName() + "开始执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                completeLatch.countDown();
            }
        }
    }
}
