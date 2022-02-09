package com.hzf.study.program.nowcoder;

import java.util.Scanner;

/**
 * @author zhuofan.han
 * @date 2021/6/4
 */
public class PasswordTransfer {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String a = in.nextLine();
            for (char c : a.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    int b = ((int)c) + 32;
                    if (b == ((int)'z')) {
                        b = (int)'a';
                    } else {
                        b++;
                    }
                    System.out.print((char)b);
                } else if (Character.isLowerCase(c)) {
                    int b = 1;
                    if (c == 'a' || c == 'b' || c == 'c') {
                        b = 2;
                    }
                    if (c == 'd' || c == 'e' || c == 'f') {
                        b = 3;
                    }
                    if (c == 'g' || c == 'h' || c == 'i') {
                        b = 4;
                    }
                    if (c == 'j' || c == 'k' || c == 'l') {
                        b = 5;
                    }
                    if (c == 'm' || c == 'n' || c == 'o') {
                        b = 6;
                    }
                    if (c == 'p' || c == 'q' || c == 'r' || c == 's') {
                        b = 7;
                    }
                    if (c == 't' || c == 'u' || c == 'v') {
                        b = 8;
                    }
                    if (c == 'w' || c == 'x' || c == 'y' || c == 'z') {
                        b = 9;
                    }
                    System.out.print(b);
                } else {
                    System.out.print(c);
                }
            }
            System.out.println();
        }
    }
}
