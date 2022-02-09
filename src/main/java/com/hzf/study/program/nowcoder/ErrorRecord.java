package com.hzf.study.program.nowcoder;

import java.util.*;
import java.util.stream.*;

/**
 * @author zhuofan.han
 * @date 2021/6/4
 */
public class ErrorRecord {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Map<String, Record> map = new HashMap<>();
        // 注意 hasNext 和 hasNextLine 的区别
        int i = 0;
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String[] b = in.nextLine().split("\\\\");
            String a = b[b.length - 1];
            if (map.containsKey(a)) {
                Record record = map.get(a);
                record.sum += 1;
            } else {
                Record record = new Record();
                record.content = a;
                record.sum = 1;
                record.position = i++;
                map.put(a, record);
            }
        }
        List<Record> records = map.values().stream().sorted((a, b) -> b.position - a.position).limit(8).sorted((a, b) -> a.position - b.position).collect(Collectors.toList());
        for (Record record : records) {
            int end = record.content.indexOf(" ");
            if (end > 16) {
                end -= 16;
                record.content = record.content.substring(end);
            }
            System.out.println(record.content + " " + record.sum);
        }
    }

    static class Record {
        public String content;
        public int sum;
        public int position;
    }
}
