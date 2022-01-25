//求解一个给定的方程，将x以字符串"x=#value"的形式返回。该方程仅包含'+'，' - '操作，变量 x 和其对应系数。
//
// 如果方程没有解，请返回“No solution”。
//
// 如果方程有无限解，则返回“Infinite solutions”。
//
// 如果方程中只有一个解，要保证返回值 x 是一个整数。
//
// 示例 1：
//
// 输入: "x+5-3+x=6+x-2"
//输出: "x=2"
//
//
// 示例 2:
//
// 输入: "x=x"
//输出: "Infinite solutions"
//
//
// 示例 3:
//
// 输入: "2x=x"
//输出: "x=0"
//
//
// 示例 4:
//
// 输入: "2x+3x-6x=x+2"
//输出: "x=-1"
//
//
// 示例 5:
//
// 输入: "x=x+2"
//输出: "No solution"
//
// Related Topics 数学 字符串 模拟 👍 81 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String solveEquation(String equation) {
        String[] sp = equation.split("=");
        String left = sp[0];
        String right = sp[1];
        int xval = 0, val = 0;
        for (String x : left.split("(?=\\+)|(?=-)")) {
            if (x.indexOf("x") >= 0) {
                xval += Integer.parseInt(tranfer(x));
            } else {
                val -= Integer.parseInt(x);
            }
        }
        for (String x : right.split("(?=\\+)|(?=-)")) {
            if (x.indexOf("x") >= 0) {
                xval -= Integer.parseInt(tranfer(x));
            } else {
                val += Integer.parseInt(x);
            }
        }
        if (xval == 0 && val == 0) {
            return "Infinite solutions";
        }
        if (xval == 0) {
            return "No solution";
        }
        return "x=" + val / xval;
    }

    public String tranfer(String x) {
        if (x.length() > 1 && x.charAt(x.length() - 2) != '+' && x.charAt(x.length() - 2) != '-') {
            return x.replace("x","");
        } else {
            return x.replace("x","1");
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
