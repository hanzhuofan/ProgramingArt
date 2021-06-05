package com.hzf.study.program.huawei;

import java.util.Scanner;

/**
 * 编写一个程序，将输入字符串中的字符按如下规则排序。
 *
 * 规则 1 ：英文字母从 A 到 Z 排列，不区分大小写。
 *
 * 如，输入： Type 输出： epTy
 *
 * 规则 2 ：同一个英文字母的大小写同时存在时，按照输入顺序排列。
 *
 * 如，输入： BabA 输出： aABb
 *
 * 规则 3 ：非英文字母的其它字符保持原来的位置。
 *
 *
 * 如，输入： By?e 输出： Be?y
 *
 *
 * 注意有多组测试数据，即输入有多行，每一行单独处理（换行符隔开的表示不同行）
 * @author zhuofan.han
 * @date 2021/6/4
 */
public class StringSorted {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int c = (int)'Z';
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String a = in.nextLine();
            char[] b = a.toCharArray();
            for (int i = 0;i < b.length - 1;i++) {
                if (!Character.isLetter(b[i])) {
                    continue;
                }
                for (int j = i + 1;j < b.length;j++) {
                    if (!Character.isLetter(b[j])) {
                        continue;
                    }
                    int x = (int)b[i];
                    int y = (int)b[j];
                    if (x > c) {
                        x -= 32;
                    }
                    if (y > c) {
                        y -= 32;
                    }
                    if (x > y) {
                        char tmp = b[i];
                        b[i] = b[j];
                        b[j] = tmp;
                    }
                }
            }
            System.out.println(new String(b));
        }
    }
}
