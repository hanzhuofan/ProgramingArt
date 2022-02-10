package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/10
 */
public class MaxLexicographical {
    public static void main(String[] args) {
        MaxLexicographical test = new MaxLexicographical();
        System.out.println(test.maxLexicographical("110100"));
    }

    /**
     * 二进制区间取反，求最大，如有num=1000，将区间[num2,...,num4]取反变为1111是字典序最大的
     *
     *
     * @param num string字符串
     * @return string字符串
     */
    public String maxLexicographical (String num) {
        // write code here
        if (num == null || "".equals(num)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean stop = false, has0 = false;
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) == '0') {
                has0 = true;
            }
            if (has0 && !stop) {
                sb.append('1');
                stop = num.charAt(i) == '1';
            } else {
                sb.append(num.charAt(i));
            }
        }
        return sb.toString();
    }
}
