package com.hzf.study.program.nowcoder;

import java.util.ArrayDeque;

/**
 * @author zhuofan.han
 * @date 2022/2/9
 */
public class LowestCommonAncestor {
    public static void main(String[] args) {
        LowestCommonAncestor lowestCommonAncestor = new LowestCommonAncestor();
        TreeNode root = new TreeNode(3);
        TreeNode root5 = new TreeNode(5);
        TreeNode root1 = new TreeNode(1);
        root.left = root5;
        root.right = root1;
        TreeNode root6 = new TreeNode(6);
        TreeNode root2 = new TreeNode(2);
        root5.left = root6;
        root5.right = root2;
        TreeNode root7 = new TreeNode(7);
        TreeNode root4 = new TreeNode(4);
        root2.left = root7;
        root2.right = root4;
        TreeNode root0 = new TreeNode(0);
        TreeNode root8 = new TreeNode(8);
        root1.left = root0;
        root1.right = root8;

        System.out.println(lowestCommonAncestor.lowestCommonAncestor(root, 6, 4));
    }
    /**
     * @param root TreeNode类
     * @param o1   int整型
     * @param o2   int整型
     * @return int整型
     */
    public int lowestCommonAncestor(TreeNode root, int o1, int o2) {
        // write code here
        if (root == null) {
            return 0;
        }
        if (root.val == o1 || root.val == o2) {
            return root.val;
        }
        int left = lowestCommonAncestor(root.left, o1, o2);
        int right = lowestCommonAncestor(root.right, o1, o2);
        if (left > 0 && right > 0) {
            return root.val;
        } else if (left > 0) {
            return left;
        } else {
            return right;
        }
    }

    boolean isTarget = false;
    public int lowestCommonAncestor2(TreeNode root, int o1, int o2) {
        // write code here
        ArrayDeque<Integer> list = new ArrayDeque<>();
        dfs(root, list, o1);
        ArrayDeque<Integer> list2 = new ArrayDeque<>();
        isTarget = false;
        dfs(root, list2, o2);
        int ans = 0;
        while (!list.isEmpty() && !list2.isEmpty() && list.peekLast().equals(list2.peekLast())) {
            ans = list.pollLast();
            list2.pollLast();
        }
        return ans;
    }

    private void dfs(TreeNode root, ArrayDeque<Integer> list, int target) {
        list.push(root.val);
        if (list.peek() == target) {
            isTarget = true;
            return;
        }
        if (root.left != null && !isTarget) {
            dfs(root.left, list, target);
            if (!isTarget) list.poll();
        }
        if (root.right != null && !isTarget) {
            dfs(root.right, list, target);
            if (!isTarget) list.poll();
        }
    }

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
