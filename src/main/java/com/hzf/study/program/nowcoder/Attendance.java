package com.hzf.study.program.nowcoder;

import java.util.Scanner;

/**
 * @author zhuofan.han
 * @date 2022/3/3
 */
public class Attendance {
    /**
     * 公司用一个字符串来表示员工的出勤信息：
     * absent 缺勤
     * late 迟到
     * leaveearly 早退
     * present 正常上班
     *
     * 能获得全勤奖的条件：
     * 缺勤次数不超过1次
     * 不能连续迟到/早退
     * 任意的连续7次考勤中，缺勤、迟到、早退的次数不超过3次
     *
     * present present  present present present late absent present late present
     * false
     * late present present present late present present late present
     * true
     * late present present present late present late present
     * false
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String[] split = scanner.nextLine().split(" ");
            int late = 0;
            int[] dp = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                int left = (i < 7 ? 0 : dp[i - 7]);
                int right = (i < 1 ? 0 : dp[i - 1]);
                if ("absent".equals(s)) {
                    dp[i] = right + 1;
                    System.out.println(false);
                    break;
                } else if ("late".equals(s)) {
                    dp[i] = right + 1;
                    late++;
                    if (late > 1) {
                        System.out.println(false);
                        break;
                    }
                }  else if ("leaveearly".equals(s)) {
                    dp[i] = right + 1;
                    late++;
                    if (late > 1) {
                        System.out.println(false);
                        break;
                    }
                } else {
                    dp[i] = right;
                    late = 0;
                }
                if (dp[i] - left >= 3) {
                    System.out.println(false);
                    break;
                }
                if (i == split.length - 1) {
                    System.out.println(true);
                }
            }
        }
    }
}
