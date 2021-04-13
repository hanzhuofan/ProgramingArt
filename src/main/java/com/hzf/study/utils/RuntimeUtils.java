package com.hzf.study.utils;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

/**
 * 系统环境工具类
 *
 * @author zhuofan.han
 * @date 2021/4/9 17:06
 */
public class RuntimeUtils {
    private static final OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
    private static final Runtime runtime = Runtime.getRuntime();

    /**
     * 获取物理内存总大小
     *
     * @return
     */
    public static long getTotalPhysicalMemorySize() {
        return systemMXBean.getTotalPhysicalMemorySize();
    }

    /**
     * 获取物理内存剩余大小
     *
     * @return
     */
    public static long getFreePhysicalMemorySize() {
        return systemMXBean.getFreePhysicalMemorySize();
    }

    /**
     * 获取物理内存已使用大小
     *
     * @return
     */
    public static long getUsedPhysicalMemorySize() {
        return systemMXBean.getTotalPhysicalMemorySize() - systemMXBean.getFreePhysicalMemorySize();
    }

    /**
     * 获取 Swap 总大小
     *
     * @return
     */
    public static long getTotalSwapSpaceSize() {
        return systemMXBean.getTotalSwapSpaceSize();
    }

    /**
     * 获取 Swap 剩余大小
     *
     * @return
     */
    public static long getFreeSwapSpaceSize() {
        return systemMXBean.getFreeSwapSpaceSize();
    }

    /**
     * 获取 Swap 已使用大小
     *
     * @return
     */
    public static long getUsedSwapSpaceSize() {
        return systemMXBean.getTotalSwapSpaceSize() - systemMXBean.getFreeSwapSpaceSize();
    }

    /**
     * 获取 JVM 最大内存
     *
     * @return
     */
    public static long getJvmMaxMemory() {
        return runtime.maxMemory();
    }

    /**
     * 获取 JVM 内存总大小
     *
     * @return
     */
    public static long getJvmTotalMemory() {
        return runtime.totalMemory();
    }

    /**
     * 获取 JVM 内存剩余大小
     *
     * @return
     */
    public static long getJvmFreeMemory() {
        return runtime.freeMemory();
    }

    /**
     * 获取 JVM 内存已使用大小
     *
     * @return
     */
    public static long getJvmUsedMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    /**
     * 获取 JVM 内存 使用率
     *
     * @return
     */
    public static long getJvmUsedMemoryLoad() {
        return getJvmUsedMemory() * 100 / getJvmTotalMemory();
    }

    /**
     * 获取系统 CPU 使用率
     *
     * @return
     */
    public static double getSystemCpuLoad() {
        return systemMXBean.getSystemCpuLoad();
    }

    /**
     * 获取 JVM 进程 CPU 使用率
     *
     * @return
     */
    public static double getProcessCpuLoad() {
        return systemMXBean.getProcessCpuLoad();
    }
}
