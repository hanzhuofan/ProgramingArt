package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class ReverseList {
    public static void main(String[] args) {
        ListNode listNode3 = new ListNode(3);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode1 = new ListNode(1);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        ListNode listNode = new ReverseList().ReverseList(listNode1);
        System.out.println(listNode);
    }

    public ListNode ReverseList(ListNode head) {
        ListNode ans = null;
        ListNode temp = head;
        while (head != null) {
            head = head.next;
            temp.next = ans;
            ans = temp;
            temp = head;
        }
        return ans;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
}
