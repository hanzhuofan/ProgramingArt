package com.hzf.study.program.huawei;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author zhuofan.han
 * @date 2022/2/7
 */
public class MergeInterval {
    public static void main(String[] args) {
        MergeInterval interval = new MergeInterval();
        ArrayList<Interval> ans = new ArrayList<>();
        ans.add(new Interval(0, 10));
        ans.add(new Interval(10, 20));
        System.out.println(interval.merge(ans));
        ArrayList<Interval> ans1 = new ArrayList<>();
        ans1.add(new Interval(80, 100));
        ans1.add(new Interval(10, 30));
        ans1.add(new Interval(150, 180));
        ans1.add(new Interval(20, 60));
        System.out.println(interval.merge(ans1));
        ArrayList<Interval> ans2 = new ArrayList<>();
        ans2.add(new Interval(1, 4));
        ans2.add(new Interval(2, 3));
        System.out.println(interval.merge(ans2));
    }

    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        merge(intervals, 0, intervals.size() - 1);

        intervals.sort(Comparator.comparingInt(a -> a.start));
        int end = 0;
        ArrayList<Interval> ans = new ArrayList<>();
        for (Interval interval : intervals) {
            if (ans.isEmpty() || end < interval.start) {
                end = interval.end;
                ans.add(interval);
            } else {
                end = Math.max(end, interval.end);
                ans.get(ans.size() - 1).end = end;
            }
        }
        return ans;
    }

    private void merge(ArrayList<Interval> intervals, int i, int j) {
        merge(intervals, i, (i + j) / 2);
        merge(intervals, (i + j) / 2, j);
    }

    static class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }

        @Override public String toString() {
            return "[" + start + ", " + end + ']';
        }
    }
}
