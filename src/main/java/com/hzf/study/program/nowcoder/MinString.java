package com.hzf.study.program.nowcoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * @author zhuofan.han
 * @date 2022/2/13
 */
public class MinString {
    public static void main(String[] args) {
        System.out.println(minString(new String[]{"abc","de"}));
        System.out.println(minString(new String[]{"a","a","b"}));
    }

    /**
     * 给定一个长度为 n 的字符串数组 strs ，请找到一种拼接顺序，使得数组中所有的字符串拼接起来组成的字符串是所有拼接方案中字典序最小的，并返回这个拼接后的字符串。
     *
     * @param strs string字符串一维数组 the strings
     * @return string字符串
     */
    public static String minString (String[] strs) {
        // write code here
        StringBuilder sb = new StringBuilder();
        Arrays.stream(strs).sorted((a, b) -> (a + b).compareTo(b + a)).forEach(sb::append);
        return sb.toString();
    }

    public static String minString2(String[] strs) {
        // write code here
        PriorityQueue<String> minPriority = new PriorityQueue<>((a, b) -> (a + b).compareTo(b + a));
        Collections.addAll(minPriority, strs);
        StringBuilder sb = new StringBuilder();
        for (String ignored : strs) {
            sb.append(minPriority.poll());
        }
        return sb.toString();
    }
}
