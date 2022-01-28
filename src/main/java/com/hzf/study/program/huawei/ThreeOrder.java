package com.hzf.study.program.huawei;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class ThreeOrder {
    public static void main(String[] args) {
        ThreeOrder order = new ThreeOrder();
        int[][] orders = order.threeOrders(null);
        System.out.println(Arrays.toString(orders));
        TreeNode treeNode = new TreeNode();
        treeNode.val = 1;
        TreeNode treeNode2 = new TreeNode();
        treeNode2.val = 2;
        TreeNode treeNode3 = new TreeNode();
        treeNode3.val = 3;
        treeNode.left = treeNode2;
        treeNode.right = treeNode3;
        int[][] ans = order.threeOrders(treeNode);
        System.out.println(Arrays.toString(ans));
    }

    /**
     * @param root TreeNode类 the root of binary tree
     * @return int整型二维数组
     */
    public int[][] threeOrders(TreeNode root) {
        // write code here
        int[][] ans = new int[3][0];
        ArrayList<Integer> res;
        if (root != null) {
            for (int i = 0; i < ans.length; i++) {
                res = new ArrayList<>();
                orders(i, res, root);
                ans[i] = res.stream().mapToInt(Integer::intValue).toArray();
            }
        }
        return ans;
    }

    private void orders(int i, ArrayList<Integer> res, TreeNode root) {
        if (root == null) {
            return;
        }
        if (i == 0) {
            res.add(root.val);
            orders(i, res, root.left);
            orders(i, res, root.right);
        } else if (i == 1) {
            orders(1, res, root.left);
            res.add(root.val);
            orders(1, res, root.right);
        } else {
            orders(2, res, root.left);
            orders(2, res, root.right);
            res.add(root.val);
        }
    }

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
    }
}
