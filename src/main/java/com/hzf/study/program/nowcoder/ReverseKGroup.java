package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/9
 */
public class ReverseKGroup {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        // ListNode listNode5 = new ListNode(5);
        listNode.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        // listNode4.next = listNode5;
        // System.out.println(new ReverseKGroup().reverseKGroup(listNode, 2));
        // System.out.println(new ReverseKGroup().reverseKGroup(listNode, 3));
        System.out.println(new ReverseKGroup().reverseKGroup(listNode, 2));
    }

    /**
     * 给定的链表是 1→2→3→4→5 对于 k=2 , 你应该返回 2→1→4→3→5 对于 k=3 , 你应该返回 3→2→1→4→5
     *
     * @param head
     *            ListNode类
     * @param k
     *            int整型
     * @return ListNode类
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // write code here
        if (head == null) {
            return null;
        }
        ListNode ans = head, tmp = head, end = head;
        for (int i = 0; i < k; i++) {
            head = head.next;
            if (i == k - 1) {
                ListNode ltmp = null, lh;
                for (int j = 0; j < k; j++) {
                    lh = tmp;
                    tmp = tmp.next;
                    lh.next = ltmp;
                    ltmp = lh;
                }
                ans = ltmp;
            } else if (head == null) {
                return ans;
            }
        }
        while (head != null) {
            tmp = head;
            ListNode tend = head;
            for (int i = 0; i < k; i++) {
                head = head.next;
                if (i == k - 1) {
                    // 翻转
                    ListNode ltmp = null, lh;
                    for (int j = 0; j < k; j++) {
                        lh = tmp;
                        tmp = tmp.next;
                        lh.next = ltmp;
                        ltmp = lh;
                    }
                    end.next = ltmp;
                    end = tend;
                } else if (head == null) {
                    end.next = tmp;
                    break;
                }
            }
        }
        return ans;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
