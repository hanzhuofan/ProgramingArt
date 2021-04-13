package com.hzf.study;

import com.hzf.study.controller.GreetingController;
import com.hzf.study.service.GreetingService;
import com.hzf.study.utils.DateHelper;
import com.hzf.study.utils.RuntimeUtils;
import com.sun.management.OperatingSystemMXBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.*;
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

        GreetingService greetingService = new GreetingService();
        System.out.println(greetingService);

        Method setGreetingService = aClass.getDeclaredMethod("setGreetingService", GreetingService.class);
        setGreetingService.invoke(greetingController, greetingService);
        System.out.println(greetingController.getGreetingService());

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

        System.out.println(DateFormat.getInstance().format(DateHelper.getEndOfDay(now)));
        System.out.println(DateFormat.getInstance().format(DateHelper.getEndOfDay(DateHelper.getStartOfDay(now) - 1000)));
        System.out.println(DateFormat.getInstance().format(DateHelper.getStartOfWeek(DateHelper.getLastWeek(now))));
        System.out.println(DateFormat.getInstance().format(DateHelper.getStartOfWeek(now)));
        System.out.println(DateFormat.getInstance().format(DateHelper.getStartOfWeek(DateHelper.getNextWeek(now))));
        System.out.println(DateFormat.getInstance().format(DateHelper.getLastMonth(now)));
        System.out.println(DateFormat.getInstance().format(DateHelper.getStartOfMonth(now)));
        System.out.println(DateFormat.getInstance().format(DateHelper.getNextMonth(now)));

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
}
