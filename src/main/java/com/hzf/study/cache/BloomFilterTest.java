package com.hzf.study.cache;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author zhuofan.han
 * @date 2022/1/23
 */
public class BloomFilterTest {
    public static void main(String[] args) {
        // 创建布隆过滤器对象
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 1500, 0.01);
        // 判断指定元素是否存在
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(2));
        // 将元素添加进布隆过滤器
        filter.put(1);
        filter.put(2);
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(2));

    }
}
