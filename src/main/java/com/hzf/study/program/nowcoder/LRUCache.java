package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class LRUCache {
    public static void main(String[] args) {
        LRUCache lru = new LRUCache();
        int[] res = lru.LRU(new int[][] {{1, 1, 1}, {1, 2, 2}, {1, 3, 2}, {2, 1}, {1, 4, 4}, {2, 2}}, 3);
        System.out.println(Arrays.toString(res));
        res = lru.LRU(new int[][] {{1,1,1},{1,2,2},{2,1},{1,3,3},{2,2},{1,4,4},{2,1},{2,3},{2,4}}, 2);
        System.out.println(Arrays.toString(res));
        res = lru.LRU(new int[][] {{1,1,1},{1,1,2},{2,1}}, 3);
        System.out.println(Arrays.toString(res));
    }
    /**
     * lru design
     * @param operators int整型二维数组 the ops
     * @param k int整型 the k
     * @return int整型一维数组
     */
    public int[] LRU (int[][] operators, int k) {
        // write code here
        LinkedList<int[]> cache = new LinkedList<>();
        ArrayList<Integer> res = new ArrayList<>();
        for (int[] operator : operators) {
            int[] cacheValue = getCache(cache, operator[1]);
            if (operator[0] == 1) {
                if (cacheValue != null) {
                    cacheValue[1] = operator[2];
                } else {
                    cache.offer(new int[]{operator[1], operator[2]});
                    if (cache.size() > k) {
                        cache.poll();
                    }
                }
            } else {
                if (cacheValue != null) {
                    cache.remove(cacheValue);
                    cache.offer(cacheValue);
                    res.add(cacheValue[1]);
                } else {
                    res.add(-1);
                }
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    private int[] getCache(LinkedList<int[]> cache, int key) {
        for (int[] ca : cache) {
            if (ca[0] == key) {
                return ca;
            }
        }
        return null;
    }
}
