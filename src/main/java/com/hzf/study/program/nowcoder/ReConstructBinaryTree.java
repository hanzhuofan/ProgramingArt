package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/24
 */
public class ReConstructBinaryTree {

    /**
     * 给定节点数为 n 的二叉树的前序遍历和中序遍历结果，请重建出该二叉树并返回它的头结点。
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建出如下图所示。
     *
     * @param pre 前序
     * @param vin 中序
     * @return
     */
    public TreeNode reConstructBinaryTree(int[] pre, int[] vin) {
        if (pre.length == 0) return null;
        return buildTree(pre, 0, pre.length - 1, vin, 0, vin.length - 1);
    }

    private TreeNode buildTree(int[] pre, int pBegin, int pEnd, int[] vin, int vBegin, int vEnd) {
        TreeNode root = new TreeNode(pre[pBegin]);
        int vinL = 0;
        for (int i = 0; i < vin.length; i++) {
            if (vin[i] == pre[pBegin]) {
                vinL = i;
                break;
            }
        }

        int left = vinL - vBegin;
        if (left > 0) {
            root.left = buildTree(pre, pBegin + 1, pEnd, vin, vBegin, vinL - 1);
        }

        int right = vEnd - vinL;
        if (right > 0) {
            root.right = buildTree(pre, pBegin + left + 1, pEnd, vin, vinL + 1, vEnd);
        }
        return root;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
