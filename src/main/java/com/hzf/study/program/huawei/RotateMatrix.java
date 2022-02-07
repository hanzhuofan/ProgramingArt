package com.hzf.study.program.huawei;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/2/7
 */
public class RotateMatrix {
    public static void main(String[] args) {
        RotateMatrix rotateMatrix = new RotateMatrix();
        int[][] data = {{1, 2, 3, 10}, {4, 5, 6, 11}, {7, 8, 9, 12}, {14, 15, 16, 13}};
        System.out.println(Arrays.deepToString(rotateMatrix.rotateMatrix(data, 4)));
        System.out.println(Arrays.deepToString(rotateMatrix.rotateMatrix2(data, 4)));
    }

    public int[][] rotateMatrix2(int[][] mat, int n) {
        // write code here
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int tmp = mat[i][j];
                mat[i][j] = mat[n - 1 - j][i];
                mat[n - 1 - j][i] = mat[n - 1 - i][n - 1 - j];
                mat[n - 1 - i][n - 1 - j] = mat[j][n - 1 - i];
                mat[j][n - 1 - i] = tmp;
            }
        }
        return mat;
    }

    public int[][] rotateMatrix(int[][] mat, int n) {
        // write code here
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = mat[n - 1 - j][i];
            }
        }
        return ans;
    }
}
