package com.hzf.study.program.huawei;

import java.util.Scanner;

/**
 * @author zhuofan.han
 * @date 2021/6/4
 */
public class PasswordCheck {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String password = in.nextLine();
            if (check(password)) {
                System.out.println("OK");
            } else {
                System.out.println("NG");
            }
        }
    }

    public static boolean check(String password) {
        if (password.length() <= 8) {
            return false;
        }

        int a = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                a += 1;
                break;
            }
        }
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                a += 1;
                break;
            }
        }
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                a += 1;
                break;
            }
        }
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                a += 1;
                break;
            }
        }
        if (a < 3) {
            return false;
        }

        for (int i = 0; i < password.length() - 5; i++) {
            for (int j = i + 2; j < password.length() - 2; j++) {
                if (password.charAt(i) == password.charAt(j)
                        && password.charAt(i + 1) == password.charAt(j + 1)
                        && password.charAt(i + 2) == password.charAt(j + 2)) {
                    return false;
                }
            }
        }
        return true;
    }
}
