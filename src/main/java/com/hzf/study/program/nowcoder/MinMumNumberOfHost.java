package com.hzf.study.program.nowcoder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author zhuofan.han
 * @date 2022/2/13
 */
public class MinMumNumberOfHost {
    public static void main(String[] args) {
        System.out.println(minMumNumberOfHost(2, new int[][]{{1,2},{2,3}}));
        System.out.println(minMumNumberOfHost(2, new int[][]{{1,3},{2,4}}));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 计算成功举办活动需要多少名主持人
     * @param n int整型 有n个活动
     * @param startEnd int整型二维数组 startEnd[i][0]用于表示第i个活动的开始时间，startEnd[i][1]表示第i个活动的结束时间
     * @return int整型
     */
    public static int minMumNumberOfHost (int n, int[][] startEnd) {
        // write code here
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        Arrays.stream(startEnd).sorted(Comparator.comparingInt(a -> a[0])).forEach(a -> {
            if (queue.isEmpty()) {
                queue.offer(a[1]);
            } else if (queue.peek() <= a[0]) {
                queue.poll();
                queue.offer(a[1]);
            } else {
                queue.offer(a[1]);
            }
        });
        return queue.size();
    }
}
