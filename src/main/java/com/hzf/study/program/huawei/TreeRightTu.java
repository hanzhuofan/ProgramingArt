package com.hzf.study.program.huawei;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/1/30
 */
public class TreeRightTu {
    public static void main(String[] args) {
        TreeRightTu tu = new TreeRightTu();
        System.out.println(Arrays.toString(tu.solve(new int[] {1,2,4,5,3}, new int[] {4,2,5,1,3})));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 求二叉树的右视图
     * @param xianxu int整型一维数组 先序遍历
     * @param zhongxu int整型一维数组 中序遍历
     * @return int整型一维数组
     */
    public int[] solve (int[] xianxu, int[] zhongxu) {
        // write code here
        TreeNode root = buildTree(xianxu, 0, zhongxu, 0, zhongxu.length - 1);

        return new int[0];
    }

    private TreeNode buildTree(int[] xianxu, int xbegin, int[] zhongxu, int zbegin, int zend) {
        TreeNode tree = new TreeNode(xianxu[xbegin]);
        int index = xbegin;
        for (int j = zbegin; j <= zend; j++) {
            if (zhongxu[j] == xianxu[xbegin]) {
                index = j;
                break;
            }
        }
        int leftOffset = index - zbegin;
        if (leftOffset > 0) {
            tree.left = buildTree(xianxu, xbegin + 1, zhongxu, zbegin, index - 1);
        }
        int rightOffset = zend - index;
        if (rightOffset > 0) {
            tree.right = buildTree(xianxu, xbegin + leftOffset + 1, zhongxu, index + 1, zend);
        }
        return tree;
    }

    static class TreeNode {
        int val;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
