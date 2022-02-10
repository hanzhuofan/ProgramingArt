package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/10
 */
public class FillArray {
    public static void main(String[] args) {
        FillArray fillArray = new FillArray();
        System.out.println(fillArray.FillArray(new int[] {0, 4, 5}, 6));
        System.out.println(fillArray.FillArray(new int[] {1, 0, 0}, 3));
        System.out.println(fillArray.FillArray(new int[] {0, 0, 0, 0, 0, 67, 0, 0}, 100));
    }

    /**
     * [0,4,5],6
     * 4
     * [1,0,0],3
     * 6
     * [0,0,0,0,0,67,0,0],100
     * 746845806
     *
     * @param a int整型一维数组
     * @param k int整型
     * @return int整型
     */
    public int FillArray(int[] a, int k) {
        // write code here
        int begin = 1;
        long ans = 1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0) {
                int n = 0, m = 0, sum = 0;
                for (int j = i; j < a.length; j++) {
                    if (a[j] != 0) {
                        n = j - i;
                        m = a[j] - begin + 1;
                        begin += m - 1;
                        i = j;
                        break;
                    }
                    if (j == a.length - 1) {
                        n = j - i + 1;
                        m = k - begin + 1;
                        begin += m - 1;
                        i = j + 1;
                    }
                }

                int[][] dp = new int[n][m];
                for (int l = 0; l < m; l++) {
                    dp[0][l] = 1;
                }
                for (int l = 0; l < n; l++) {
                    dp[l][m - 1] = 1;
                }
                for (int l = 1; l < n; l++) {
                    for (int o = m - 2; o >= 0; o--) {
                        dp[l][o] = (dp[l - 1][o] + dp[l][o + 1]) % (int)(Math.pow(10, 9) + 7);
                    }
                }

                for (int l = 0; l < m; l++) {
                    sum = (sum + dp[n - 1][l]) % (int)(Math.pow(10, 9) + 7);
                }
                ans = (ans * sum) % (int)(Math.pow(10, 9) + 7);
            } else {
                begin = a[i];
            }
        }
        return (int)ans;
    }
}
