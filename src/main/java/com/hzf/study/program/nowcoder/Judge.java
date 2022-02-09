package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/1/30
 */
public class Judge {

    public static void main(String[] args) {
        Judge judge = new Judge();
        System.out.println(judge.judge("absba"));
        System.out.println(judge.judge("ranko"));
        System.out.println(judge.judge("yamatomaya"));
        System.out.println(judge.judge("a"));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param str string字符串 待判断的字符串
     * @return bool布尔型
     */
    public boolean judge(String str) {
        // write code here
        for (int i = 0, j = str.length() - 1; i < str.length() / 2; i++, j--) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}
