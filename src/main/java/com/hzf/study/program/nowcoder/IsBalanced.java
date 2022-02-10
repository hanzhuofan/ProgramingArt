package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/10
 */
public class IsBalanced {
    boolean isBalanced = true;
    public boolean IsBalanced_Solution(TreeNode root) {
        if (root == null) {
            return true;
        }
        isBalanced(root);
        return isBalanced;
    }

    private int isBalanced(TreeNode root) {
        if (root == null) return 0;
        int left = isBalanced(root.left);
        int right = isBalanced(root.right);
        if (Math.abs(left - right) > 1) {
            isBalanced = false;
        }
        return Math.max(left, right) + 1;
    }

    static class TreeNode {
        TreeNode left = null;
        TreeNode right = null;
    }
}
