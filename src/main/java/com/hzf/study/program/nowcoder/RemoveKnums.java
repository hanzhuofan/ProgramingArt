package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/14
 */
public class RemoveKnums {

    /**
     * 给定一个以字符串表示的数字 num 和一个数字 k ，从 num 中移除 k 位数字，使得剩下的数字最小。如果可以删除全部数字则剩下 0
     *
     *
     * @param num string字符串
     * @param k int整型
     * @return string字符串
     */
    public String removeKnums (String num, int k) {
        // write code here
        if (num.length() == k) {
            return "0";
        }

        StringBuilder sb = new StringBuilder(num);
        for(int i = 0; i < k; i++) {
            int index = 0;
            for(int j = 1; j < sb.length() && sb.charAt(j) > sb.charAt(j - 1); j++) {
                index = j;
            }
            sb.delete(index, index + 1);
            while(sb.length() > 1 && sb.charAt(0) == '0') {
                sb.delete(0, 1);
            }
        }
        return sb.toString();
    }
}
