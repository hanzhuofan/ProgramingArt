package com.hzf.study.program.huawei;

import java.util.*;

/**
 * @author zhuofan.han
 * @date 2021/6/4
 */
public class DeleteStringMin {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            Map<Character, Integer> map = new HashMap<>();
            String a = in.nextLine();
            for (char c : a.toCharArray()) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }
            int min = map.values().stream().min((a1, b1) -> a1 - b1).get();
            Set<Character> set = new HashSet<>();
            for (Character c : map.keySet()) {
                if (map.get(c) == min) {
                    set.add(c);
                }
            }
            char[] r = new char[a.length() - set.size() * min];
            int i = 0;
            for (char c : a.toCharArray()) {
                if (!set.contains(c)) {
                    r[i] = c;
                    i++;
                }
            }
            System.out.println(new String(r));
        }
    }
}
