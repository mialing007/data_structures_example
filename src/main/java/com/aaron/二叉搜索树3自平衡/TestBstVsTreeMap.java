package com.aaron.二叉搜索树3自平衡;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class TestBstVsTreeMap {
    public static final int MAX = 20480;
    private Random random = new Random();

    private final int MAX2 = 65535;

    /**
     * 测试随机数据写入速度
     */
    @Test
    public void testBSTRandom() {
        AVLMap<Integer, String> map = new AVLMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(random.nextInt(MAX), random.nextInt(MAX)+"");
        }
        //map.checkBalance();
        for (int i = 0; i < MAX; i++) {
            map.get(random.nextInt(MAX));
        }
    }

    /**
     * treemap 随机数据写入速度
     */
    @Test
    public void testTreeMapRandom() {
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(random.nextInt(MAX), random.nextInt(MAX)+"");
        }

        for (int i = 0; i < MAX; i++) {
            map.get(random.nextInt(MAX));
        }
    }

    /**
     * 有序数据，写入速度，（自平衡搜索树写入快，  但是非自平衡的搜索树写入慢，因为非自平衡写入顺序数据会变成链表）
     */
    @Test
    public void testBSTIncremnet() {
        AVLMap<Integer, String> map = new AVLMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(i, random.nextInt(MAX)+"");
        }
        //map.checkBalance();
        for (int i = 0; i < MAX; i++) {
            map.get(random.nextInt(MAX));
        }
    }

    /**
     * TreeMap插入顺序的数据。
     */
    @Test
    public void testTreeMapIncremnet() {
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(i, random.nextInt(MAX)+"");
        }

        for (int i = 0; i < MAX; i++) {
            map.get(random.nextInt(MAX));
        }
    }

    @Test
    public void testAVLDelete() {
        AVLMap<Integer, String> map1 = new AVLMap<>();
        TreeMap<Integer, String> map2 = new TreeMap<>();
        for (int i = 0; i < MAX2; i++) {
            int key = random.nextInt(MAX2);
            String value = random.nextInt(MAX2) + "";
            map1.put(key, value);
            map2.put(key, value);
        }
        for (int i = 0; i < MAX2 >> 1; i++) {
            int key = random.nextInt(MAX2);
            map1.remove(key);
            map2.remove(key);
        }
        map1.checkBalance();
        Assert.assertTrue(map1.size() == map2.size());
        Iterator<Map.Entry<Integer, String>> itr1 = map1.iterator();
        Iterator<Map.Entry<Integer, String>> itr2 = map2.entrySet().iterator();
        while (itr1.hasNext() && itr2.hasNext()) {
            Assert.assertTrue(itr1.next().getKey().equals(itr2.next().getKey()));
        }
        Assert.assertTrue(!itr1.hasNext() && !itr2.hasNext());
    }

    @Test
    public void test() {
        AVLMap<Integer, Integer> map = new AVLMap<>();
        map.put(10, 10);
        map.put(5, 5);
        map.put(15, 15);
        map.put(2, 2);
        map.put(6, 6);
        map.put(14, 14);
        map.put(20, 20);
        map.put(16, 16);
        //map.put(7, 7);
        map.levelOrder();
        Iterator<AVLEntry<Integer, Integer>> itr = map.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next().key);
        }

        map.remove(14);

        map.levelOrder();
        Iterator<AVLEntry<Integer, Integer>> itr1 = map.iterator();
        while (itr1.hasNext()) {
            System.out.println(itr1.next().key);
        }
    }

    @Test
    public void test1() {
        AVLMap<Integer, Integer> map = new AVLMap<>();
        for (int i = 0 ; i < 10; i++) {
            map.put(i,i);
            map.put(i,i);
        }

    }

}
