package com.hzf.study.program.nowcoder;

import java.util.*;

/**
 * 描述
 * 请解析IP地址和对应的掩码，进行分类识别。要求按照A/B/C/D/E类地址归类，不合法的地址和掩码单独归类。
 *
 * 所有的IP地址划分为 A,B,C,D,E五类
 *
 * A类地址1.0.0.0~126.255.255.255;
 *
 * B类地址128.0.0.0~191.255.255.255;
 *
 * C类地址192.0.0.0~223.255.255.255;
 *
 * D类地址224.0.0.0~239.255.255.255；
 *
 * E类地址240.0.0.0~255.255.255.255
 *
 *
 * 私网IP范围是：
 *
 * 10.0.0.0～10.255.255.255
 *
 * 172.16.0.0～172.31.255.255
 *
 * 192.168.0.0～192.168.255.255
 *
 *
 * 子网掩码为二进制下前面是连续的1，然后全是0。（例如：255.255.255.32就是一个非法的掩码）
 * 注意二进制下全是1或者全是0均为非法
 *
 * 注意：
 * 1. 类似于【0.*.*.*】和【127.*.*.*】的IP地址不属于上述输入的任意一类，也不属于不合法ip地址，计数时可以忽略
 * 2. 私有IP地址和A,B,C,D,E类地址是不冲突的
 *
 * 输入描述：
 * 多行字符串。每行一个IP地址和掩码，用~隔开。
 *
 * 输出描述：
 * 统计A、B、C、D、E、错误IP地址或错误掩码、私有IP的个数，之间以空格隔开。
 *
 * 示例1
 * 输入：
 * 10.70.44.68~255.254.255.0
 * 1.0.0.1~255.0.0.0
 * 192.168.0.2~255.255.255.0
 * 19..0.~255.255.255.0
 * 复制
 * 输出：
 * 1 0 1 0 0 2 1
 */
public class IPCheck {
    public static void main(String[] args) {
        IPCheck ipCheck = new IPCheck();
        System.out.println(ipCheck.solve("172.16.254.1"));
        System.out.println(ipCheck.solve("172.16.254.01"));
        System.out.println(ipCheck.solve("256.256.256.256"));
        System.out.println(ipCheck.solve("2001:db8:85a3:0:0:8A2E:0370:7334"));
        System.out.println(ipCheck.solve("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
        System.out.println(ipCheck.solve("2001:0db8:85a3::8A2E:0370:7334"));
        System.out.println(ipCheck.solve("02001:0db8:85a3:0000:0000:8a2e:0370:7334"));

        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int[] r = new int[]{0, 0, 0, 0, 0, 0, 0};
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String[] a = in.nextLine().split("~");
            String[] ip = a[0].split("\\.");
            String[] ma = a[1].split("\\.");
            if (!check(ip, ma)) {
                r[5] += 1;
            } else {
                int c = Integer.parseInt(ip[0]);
                int d = Integer.parseInt(ip[1]);
                if (c >= 1 && c <= 126) {
                    if (c == 10) {
                        r[6] += 1;
                    }
                    r[0] += 1;
                }
                if (c >= 128 && c <= 191) {
                    if (c == 172 && (d >= 16 && d <= 31)) {
                        r[6] += 1;
                    }
                    r[1] += 1;
                }
                if (c >= 192 && c <= 223) {
                    if (c == 192 && d == 168) {
                        r[6] += 1;
                    }
                    r[2] += 1;
                }
                if (c >= 224 && c <= 239) {
                    r[3] += 1;
                }
                if (c >= 240 && c <= 255) {
                    r[4] += 1;
                }
            }
        }

        for (int i = 0;i < r.length;i++) {
            System.out.print(r[i]);
            if (i == r.length - 1) {
                System.out.println();
            } else {
                System.out.print(" ");
            }
        }
    }

    /**
     * 验证IP地址
     * @param IP string字符串 一个IP地址字符串
     * @return string字符串
     */
    public String solve (String IP) {
        // write code here
        if (IP.contains(".") && checkIpv4(IP)) {
            return "IPv4";
        } else if (IP.contains(":") && checkIpv6(IP)) {
            return "IPv6";
        } else {
            return "Neither";
        }
    }

    private boolean checkIpv6(String ip) {
        String[] split = ip.split(":");
        if (split.length != 8) return false;
        for (int i = 0; i < split.length; i++) {
            String num = split[i];
            if (!num.matches("[0-9a-fA-F]{1,4}")) return false;
        }
        return true;
    }

    private boolean checkIpv4(String ip) {
        String[] split = ip.split("\\.");
        if (split.length != 4) return false;
        for (int i = 0; i < split.length; i++) {
            String num = split[i];
            if (!num.matches("[0-9]|[1-9][0-9]|[1][0-9][0-9]|[2][0-4][0-9]|[2][5][0-5]")) return false;
        }
        return true;
    }

    public static boolean check(String[] ip, String[] ma) {
        if (ip.length != 4 || ma.length != 4) {
            return false;
        }
        boolean isOne = true;
        for (int i = 0;i < 4;i++) {
            int a = 128;
            int b = Integer.parseInt(ma[i]);
            int c = Integer.parseInt(ip[i]);
            if (b < 0 || b > 255 || c < 0 || c > 255) {
                return false;
            }
            for (int j = 0;j < 8;j++) {
                if (isOne) {
                    if ((a & b) == 0) {
                        isOne = false;
                    }
                } else {
                    if ((a & b) != 0) {
                        return false;
                    }
                }
                a = a >>> 1;
            }
        }
        return !isOne;
    }
}