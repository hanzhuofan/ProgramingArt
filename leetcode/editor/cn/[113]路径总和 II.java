//给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。 
//
// 叶子节点 是指没有子节点的节点。 
//
// 
// 
// 
//
// 示例 1： 
//
// 
//输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
//输出：[[5,4,11,2],[5,8,4,5]]
// 
//
// 示例 2： 
//
// 
//输入：root = [1,2,3], targetSum = 5
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：root = [1,2], targetSum = 0
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点总数在范围 [0, 5000] 内 
// -1000 <= Node.val <= 1000 
// -1000 <= targetSum <= 1000 
// 
// 
// 
// Related Topics 树 深度优先搜索 回溯 二叉树 👍 659 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> paths = new ArrayList<>();
        int[] record = new int[5000];
        pathSum(root, targetSum, 0, paths, record, 0);
        return paths;
    }
    public void pathSum(TreeNode root, int targetSum, int curr, List<List<Integer>> paths, int[] record, int index) {
        if (root == null) return;
        record[index] = root.val;
        curr += root.val;
        if (root.left == null && root.right == null) {
            if (curr == targetSum) {
                List<Integer> path = new ArrayList<>();
                for (int i = 0; i <= index; i++) {
                    path.add(record[i]);
                }
                paths.add(path);
            }
            return;
        }
        pathSum(root.left, targetSum, curr, paths, record, index + 1);
        pathSum(root.right, targetSum, curr, paths, record, index + 1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
