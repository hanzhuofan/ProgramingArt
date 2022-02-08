package com.hzf.study.program.huawei;

/**
 * @author zhuofan.han
 * @date 2022/1/30
 */
public class DaoNum {
    public static void main(String[] args) {
        DaoNum num = new DaoNum();
        System.out.println(
            num.solve(new char[][] {
                {'1', '1', '0', '0', '0'},
                {'0', '1', '0', '1', '1'},
                {'0', '0', '0', '1', '1'}
            }));
    }

    /**
     * 判断岛屿数量
     *
     * @param grid char字符型二维数组
     * @return int整型
     */
    public int solve(char[][] grid) {
        // write code here
        int num = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    clear(grid, i, j);
                    num++;
                }
            }
        }
        return num;
    }

    private void clear(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i > grid.length - 1 || j > grid[i].length - 1) return;
        if (grid[i][j] == '1') {
            grid[i][j] = '0';
            clear(grid, i - 1, j);
            clear(grid, i + 1, j);
            clear(grid, i, j - 1);
            clear(grid, i, j + 1);
        }
    }
}
