package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/1/30
 */
public class MaxDepth {
    public static void main(String[] args) {

    }

    /**
     * @param root TreeNode类
     * @return int整型
     */
    public int maxDepth(TreeNode root) {
        // write code here
       return maxDepth(root, 0);
    }

    private int maxDepth(TreeNode root, int i) {
        if (root == null) {
            return i;
        }

        return Math.max(maxDepth(root.left, i + 1), maxDepth(root.right, i + 1));
    }

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
    }
}
