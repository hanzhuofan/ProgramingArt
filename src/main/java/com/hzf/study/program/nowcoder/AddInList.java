package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/1/29
 */
public class AddInList {
    public static void main(String[] args) {
        ListNode head1 = new ListNode(9);
        ListNode head3 = new ListNode(3);
        ListNode head4 = new ListNode(7);
        head1.next = head3;
        head3.next = head4;
        ListNode head2 = new ListNode(6);
        ListNode head5 = new ListNode(3);
        head2.next = head5;
        System.out.println(new AddInList().addInList(head1, head2));
    }

    /**
     * @param head1 ListNode类
     * @param head2 ListNode类
     * @return ListNode类
     */
    public ListNode addInList(ListNode head1, ListNode head2) {
        // write code here
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        head1 = transfer(head1);
        head2 = transfer(head2);
        ListNode ans = new ListNode(0);
        int yu = 0;
        while (head1 != null || head2 != null) {
            int head1val = head1 != null ? head1.val : 0;
            int head2val = head2 != null ? head2.val : 0;
            int sum = head1val + head2val + yu;
            ListNode tmp = new ListNode(sum % 10);
            tmp.next = ans.next;
            ans.next = tmp;
            yu = sum >= 10 ? 1 : 0;

            head1 = head1 != null ? head1.next : null;
            head2 = head2 != null ? head2.next : null;
        }
        if (yu == 1) {
            ListNode tmp = new ListNode(1);
            tmp.next = ans.next;
            ans.next = tmp;
        }
        return ans.next;
    }

    private ListNode transfer(ListNode head1) {
        ListNode tmp1 = null, head1end = head1;
        while (head1end != null) {
            head1 = head1.next;
            head1end.next = tmp1;
            tmp1 = head1end;
            head1end = head1;
        }
        return tmp1;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
