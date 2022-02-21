package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/21
 */
public class MinTrace {

    /**
     * 给定一个正三角形数组，自顶到底分别有 1，2，3，4，5...，n 个元素，找出自顶向下的最小路径和。
     * 每一步只能移动到下一行的相邻节点上，相邻节点指下行种下标与之相同或下标加一的两个节点。
     *
     *
     * @param triangle int整型二维数组
     * @return int整型
     */
    public static int minTrace (int[][] triangle) {
        // write code here
        int len = triangle.length;
        if (len == 1) {
            return triangle[0][0];
        }
        for (int i = 1; i < len - 1; i++) {
            triangle[i][0] += triangle[i - 1][0];
            for (int j = 1; j < triangle[i].length - 1; j++) {
                triangle[i][j] += Math.min(triangle[i - 1][j], triangle[i - 1][j - 1]);
            }
            triangle[i][i] += triangle[i - 1][i - 1];
        }

        triangle[len - 1][0] += triangle[len - 2][0];
        int ans = triangle[len - 1][0];
        for (int j = 1; j < triangle[len - 1].length - 1; j++) {
            triangle[len - 1][j] += Math.min(triangle[len - 2][j], triangle[len - 2][j - 1]);
            ans = Math.min(ans, triangle[len - 1][j]);
        }
        triangle[len - 1][len - 1] += triangle[len - 2][len - 2];
        ans = Math.min(ans, triangle[len - 1][len - 1]);
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(minTrace(new int[][]{{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}}));
    }
}
