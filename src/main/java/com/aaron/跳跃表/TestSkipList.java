package com.aaron.跳跃表;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;

public class TestSkipList {

    @Test
    public void testSkipList() {
        SkipList<String> list = new SkipList<String>();
        System.out.println(list);
        list.put(2, "yan");
        list.put(1, "co");
        list.put(3, "feng");
        list.put(1, "cao");//测试同一个key值
        list.put(4, "曹");
        list.put(6, "丰");
        list.put(5, "艳");
        System.out.println(list);
        System.out.println(list.size());


        System.out.println("----------------------");
        list.printSkipByLevel();

        list.deleteNode(4);

        System.out.println("----------------------");
        list.printSkipByLevel();

        System.out.println(list);
    }

    @Test
    public void testSkipListVsLinkedList() {
        int MAX = 20800;
        Random random = new Random();
        SkipList<String> list1 = new SkipList<>();
        LinkedList list2 = new LinkedList();
        for (int i = 0; i < MAX; i++) {
            int key = random.nextInt(MAX);
            list1.put(key, key+"");
            list2.add(key);
        }

        long start1 = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            list1.search(i);
        }
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            list2.contains(i);
        }
        long end2 = System.currentTimeMillis();

        System.out.println("跳表查询时间："+(end1-start1));
        System.out.println("链表查询时间："+(end2-start2));
    }
}
