package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/10
 */
public class FlipChess {
    /**
     * 在 4x4 的棋盘上摆满了黑白棋子，黑白两色棋子的位置和数目随机，其中0代表白色，1代表黑色；左上角坐标为 (1,1) ，右下角坐标为 (4,4) 。
     * 现在依次有一些翻转操作，要对以给定翻转坐标(x,y)（也即第x行第y列）为中心的上下左右四个棋子的颜色进行翻转。
     * 给定两个数组 A 和 f ，分别代表 初始棋盘 和 哪些要进行翻转的位置(x,y) ，请返回经过所有翻转操作后的棋盘。
     *
     * @param A int整型二维数组
     * @param f int整型二维数组
     * @return int整型二维数组
     */
    public int[][] flipChess (int[][] A, int[][] f) {
        // write code here
        int m = A.length, n = A[0].length;
        for (int i = 0; i < f.length; i++) {
            int x = f[i][0] - 1,y = f[i][1] - 1;
            if (x > 0) A[x - 1][y] = 1 - A[x - 1][y];
            if (y > 0) A[x][y - 1] = 1 - A[x][y - 1];
            if (x < m - 1) A[x + 1][y] = 1 - A[x + 1][y];
            if (y < n - 1) A[x][y + 1] = 1 - A[x][y + 1];
        }
        return A;
    }
}
