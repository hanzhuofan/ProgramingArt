package com.hzf.study.program.nowcoder;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 有n个房间，开始时你位于0号房间。每个房间有不同的号码：0，1，2，…，N-1，并且房间里可能有一些钥匙，能使你进入下一个房间。
 * 在形式上，对于每个房间i都有一个钥匙列表rooms[i]，每个钥匙rooms[i][j]由[0,1,2,…,N-1]中的一个整数表示，其中N=rooms.length。
 * 钥匙rooms[i][j]=v可以打开编号为v的房间。最初，除0号房间外的其余所有房间都被锁住。你可以自由的在房间之间来回走动。如果能能进入每个房间返回true，否则返回false。
 *
 * @author zhuofan.han
 * @date 2021/6/23
 */
public class RoomKey {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int n = Integer.parseInt(scanner.nextLine());
            int[][] room = new int[n][];
            Set<Integer> keys = new HashSet<>();
            Set<Integer> closeRoom = new HashSet<>();
            for (int i = 0; i < n; i++) {
                closeRoom.add(i);
                String[] line = scanner.nextLine().split(" ");
                room[i] = new int[line.length];
                for (int j = 0; j < line.length; j++) {
                    room[i][j] = Integer.parseInt(line[j]);
                }
            }
            openRoom(0, room, closeRoom, keys);
            System.out.println(closeRoom.isEmpty());
        }
    }

    private static void openRoom(int i, int[][] room, Set<Integer> closeRoom, Set<Integer> keys) {
        for (int key : room[i]) {
            keys.add(key);
        }
        closeRoom.remove(i);
        keys.retainAll(closeRoom);
        for (Integer key : keys) {
            openRoom(key, room, closeRoom, new HashSet<>(keys));
        }
    }
}
/*
4
1 2
2 3
1 3
1 3
true
5
1 2
2 3
1 3
1 3
1 3
false
 */