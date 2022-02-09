package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class JumpFloor {
    public static void main(String[] args) {
        JumpFloor floor = new JumpFloor();
        System.out.println(floor.jumpFloor(40));
        System.out.println(floor.jump(40));
    }

    public int jumpFloor(int target) {
        int d1 = 1, d2 = 1;
        for (int i = 1; i < target; i++) {
            int temp = d2;
            d2 = d2 + d1;
            d1 = temp;
        }
        return d2;
    }

    public int jump(int target) {
        if (target == 1 || target == 0) return 1;
        return jump(target - 1) + jump(target - 2);
    }
}
