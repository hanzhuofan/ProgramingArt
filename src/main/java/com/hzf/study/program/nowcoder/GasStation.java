package com.hzf.study.program.nowcoder;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/2/13
 */
public class GasStation {

    public static void main(String[] args) {
        System.out.println(gasStation(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));
        System.out.println(gasStation(new int[]{0,10}, new int[]{9,1}));
        System.out.println(gasStation(new int[]{2,3,4}, new int[]{3,4,5}));
        System.out.println(gasStation2(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));
        System.out.println(gasStation2(new int[]{0,10}, new int[]{9,1}));
        System.out.println(gasStation2(new int[]{2,3,4}, new int[]{3,4,5}));
    }

    /**
     * 在一条环路上有 n 个加油站，其中第 i 个加油站有 gas[i] 升油，假设汽车油箱容量无限，从第 i 个加油站驶往第 (i+1)%n 个加油站需要花费 cost[i] 升油。
     *
     * 请问能否绕环路行驶一周，如果可以则返回出发的加油站编号，如果不能，则返回 -1。
     * 题目数据可以保证最多有一个答案。
     *
     *
     * @param gas int整型一维数组
     * @param cost int整型一维数组
     * @return int整型
     */
    public static int gasStation (int[] gas, int[] cost) {
        // write code here
        int len = gas.length;
        for (int i = 0; i < len; i++) {
            gas[i] = gas[i] - cost[i];
        }
        int ans = 0;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                sum += gas[(i + j) % len];
                if (sum < 0) {
                    ans++;
                    sum = 0;
                    break;
                } else if (j == len - 1) {
                    return ans;
                }
            }
        }

        return -1;
    }
    public static int gasStation2(int[] gas, int[] cost) {
        // write code here
        int ans = 0, reset = 0, sum = 0;
        for (int i = 0; i < gas.length; i++) {
            sum += gas[i] - cost[i];
            reset += gas[i] - cost[i];
            if (reset < 0) {
                reset = 0;
                ans = i + 1;
            }
        }
        return sum < 0 ? -1 : ans;
    }
}
