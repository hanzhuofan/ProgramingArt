package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/23
 */
public class YangHuiSanJiao {

    /**
     * 给定一个非负整数 num ，生成杨辉三角的前 num 行。
     * 杨辉三角中，每个数是左上方和右上方的数之和。
     *
     *
     * @param num int整型
     * @return int整型二维数组
     */
    public static int[][] generate (int num) {
        // write code here
        int[][] ans = new int[num][];
        ans[0] = new int[]{1};
        for (int i = 1; i < num; i++) {
            ans[i] = new int[i + 1];
            ans[i][0] = 1;
            ans[i][i] = 1;
            for (int j = 1; j < i; j++) {
                ans[i][j] = ans[i - 1][j - 1] + ans[i - 1][j];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        generate(1);
        generate(2);
        generate(3);
        generate(4);
    }
}
