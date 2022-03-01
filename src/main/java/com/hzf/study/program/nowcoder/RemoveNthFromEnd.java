package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/3/1
 */
public class RemoveNthFromEnd {

    /**
     *
     * @param head ListNode类
     * @param n int整型
     * @return ListNode类
     */
    public ListNode removeNthFromEnd (ListNode head, int n) {
        // write code here
        ListNode left = head, right = head;
        int num = 1;
        for (int i = 0; i < n; i++) {
            right = right.next;
            num++;
        }

        while (right != null && right.next != null) {
            right = right.next;
            num++;
            left = left.next;
        }
        if (num == n) {
            head = head.next;
        } else {
            left.next = left.next.next;
        }
        return head;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
