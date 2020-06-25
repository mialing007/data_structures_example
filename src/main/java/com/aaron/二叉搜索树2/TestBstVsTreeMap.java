package com.aaron.二叉搜索树2;

import org.junit.Test;

import java.util.Random;
import java.util.TreeMap;

/**
 * 测试 非平衡二叉树，
 * 顺序插入时候特别慢
 * testBSTIncremnet该方法插入特别慢，因为顺序插入，变成了一条链表，
 */
public class TestBstVsTreeMap {
    public static final int MAX = 20480;
    private Random random = new Random();

    @Test
    public void testBSTRandom() {
        AVLMap<Integer, String> map = new AVLMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(random.nextInt(MAX), random.nextInt(MAX)+"");
        }

        for (int i = 0; i < MAX; i++) {
            map.get(random.nextInt(MAX));
        }
    }

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
     * 耗时特别长。
     */
    @Test
    public void testBSTIncremnet() {
        AVLMap<Integer, String> map = new AVLMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(i, random.nextInt(MAX)+"");
        }

        for (int i = 0; i < MAX; i++) {
            map.get(random.nextInt(MAX));
        }
    }

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


}
