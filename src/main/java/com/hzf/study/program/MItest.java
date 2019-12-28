package com.hzf.study.program;

import java.util.Scanner;

/**
 * 输入一个数判断是否是2的幂次方，输出最接近的小于数
 */
public class MItest {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (s.hasNext()) {
            int zs = s.nextInt();
            exeTest(zs);
        }
    }

    private static void exeTest(int zs) {
        if (zs <= 0) {
            System.out.println(zs + " :不是2的幂次方");
        } else {
            String zss = Integer.toBinaryString(zs);
            if (checkBinaryString(zss)) {
                System.out.println(zs + " :是2的" + (zss.length() - 1) + "次方");
            } else {
                System.out.println(zs + " :不是2的幂次方");
                System.out.println("最接近的数 " + (int) Math.pow(2, zss.length() - 1));
            }
        }
    }

    private static boolean checkBinaryString(String zss) {
        for (int i = 1; i < zss.length(); i++) {
            if (zss.charAt(i) == '1') {
                return false;
            }
        }
        return true;
    }
}
