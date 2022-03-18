package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.Stack;

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
    public static void printTree(TreeNode root) {
        Stack<TreeNode> nodes = new Stack<>();
        nodes.push(root);
        boolean fx = false;
        int count = 1;
        while (!nodes.isEmpty()) {
            Stack<TreeNode> tmp = new Stack<>();
            for (int i = 0; i < count; i++) {
                TreeNode node = nodes.pop();
                System.out.println(node.val);
                if (fx) {
                    if (node.right != null) {
                        tmp.push(node.right);
                    }
                    if (node.left != null) {
                        tmp.push(node.left);
                    }
                } else {
                    if (node.left != null) {
                        tmp.push(node.left);
                    }
                    if (node.right != null) {
                        tmp.push(node.right);
                    }
                }
            }
            nodes = tmp;
            fx = !fx;
            count = nodes.size();
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
