package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/2/7
 */
public class RotateMatrix {
    public static void main(String[] args) {
        RotateMatrix rotateMatrix = new RotateMatrix();
        int[][] data = {{1, 2, 3, 10}, {4, 5, 6, 11}, {7, 8, 9, 12}, {14, 15, 16, 13}};
//        System.out.println(Arrays.deepToString(rotateMatrix.rotateMatrix(data, 4)));
//        System.out.println(Arrays.deepToString(rotateMatrix.rotateMatrix2(data, 4)));
        System.out.println(rotateMatrix.spiralOrder(data));
    }

    public int[][] rotateMatrix2(int[][] mat, int n) {
        // write code here
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n / 2; j++) {
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

    public ArrayList<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (matrix.length == 0 || matrix[0].length == 0) return ans;
        int i = 0, j = 0, m = matrix.length, n = matrix[0].length, fx = 0;
        while (matrix[i][j] != -101) {
            ans.add(matrix[i][j]);
            matrix[i][j] = -101;
            switch (fx) {
                case 0:
                    if (j + 1 < n && matrix[i][j + 1] != -101) {
                        j++;
                    } else {
                        fx = 1;
                        i++;
                    }
                    break;
                case 1:
                    if (i + 1 < m && matrix[i + 1][j] != -101) {
                        i++;
                    } else {
                        fx = 2;
                        j--;
                    }
                    break;
                case 2:
                    if (j - 1 >= 0 && matrix[i][j - 1] != -101) {
                        j--;
                    } else {
                        fx = 3;
                        i--;
                    }
                    break;
                case 3:
                    if (i - 1 >= 0 && matrix[i - 1][j] != -101) {
                        i--;
                    } else {
                        fx = 0;
                        j++;
                    }
                    break;
            }
            if (i < 0 || i > m - 1 || j < 0 || j > n - 1 ) break;
        }
        return ans;
    }
}
