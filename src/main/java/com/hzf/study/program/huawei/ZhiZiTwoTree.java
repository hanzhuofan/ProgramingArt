package com.hzf.study.program.huawei;

import java.util.ArrayList;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class ZhiZiTwoTree {
    public static void main(String[] args) {
        ZhiZiTwoTree tree = new ZhiZiTwoTree();
        TreeNode treeNode1 = new TreeNode(8);
        TreeNode treeNode2 = new TreeNode(6);
        TreeNode treeNode3 = new TreeNode(10);
        TreeNode treeNode4 = new TreeNode(5);
        TreeNode treeNode5 = new TreeNode(7);
        TreeNode treeNode6 = new TreeNode(9);
        TreeNode treeNode7 = new TreeNode(11);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode3.left = treeNode6;
        treeNode3.right = treeNode7;
        System.out.println(tree.Print(treeNode1));
    }

    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        if (pRoot == null) {
            return ans;
        }
        TreeNode[] list = new TreeNode[1500];
        int left = 0, right = 0;
        list[right++] = pRoot;
        boolean sort = false;
        do {
            ArrayList<Integer> point = new ArrayList<>();
            int size = right - left;
            for (int i = 0; i < size; i++) {
                TreeNode node = list[left++];
                point.add(node.val);
                if (node.left != null) {
                    list[right++] = node.left;
                }
                if (node.right != null) {
                    list[right++] = node.right;
                }
            }
            if (sort) {
                ArrayList<Integer> pointSort = new ArrayList<>();
                for (int i = point.size() - 1; i >= 0; i--) {
                    pointSort.add(point.get(i));
                }
                point = pointSort;
            }
            ans.add(point);
            sort = !sort;
        } while (left != right);
        return ans;
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
