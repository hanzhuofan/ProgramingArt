package com.hzf.study.program.nowcoder;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author zhuofan.han
 * @date 2022/2/7
 */
public class TopKStrings {
    public static void main(String[] args) {
        TopKStrings topKStrings = new TopKStrings();
        System.out.println(Arrays.deepToString(topKStrings.topKstrings(new String[] {"a", "b", "c", "b"}, 2)));
        System.out.println(Arrays.deepToString(topKStrings.topKstrings(new String[] {"123","123","231","32"}, 2)));
        System.out.println(Arrays.deepToString(topKStrings.topKstrings(new String[] {"abcd","abcd","abcd","pwb2","abcd","pwb2","p12"}, 3)));
    }

    /**
     * return topK string
     * @param strings string字符串一维数组 strings
     * @param k int整型 the k
     * @return string字符串二维数组
     */
    public String[][] topKstrings (String[] strings, int k) {
        // write code here
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : strings) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        String[][] ans = new String[k][2];
        final int[] i = {0};
        map.entrySet().stream().sorted((a, b) -> {
            int com = b.getValue() - a.getValue();
            if (com == 0) {
                return a.getKey().compareTo(b.getKey());
            }
            return com;
        }).limit(k).forEach(a -> {
            ans[i[0]][0] = a.getKey();
            ans[i[0]][1] = a.getValue().toString();
            i[0]++;
        });
        return ans;
    }
}
