package com.aaron.二叉搜索树2;

import apple.laf.JRSUIUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestBST {
    private Random random = new Random();
    private final int MAX1 = 16;

    @Test
    public void testPutAndItr() {
        AVLMap<Integer, String> map = new AVLMap<Integer, String>();
        for (int i = 0; i < MAX1; i++) {
            map.put(random.nextInt(MAX1), random.nextInt(MAX1)+"");
        }

        Iterator<AVLEntry<Integer, String>> itr = map.iterator();
        while (itr.hasNext()) {
            AVLEntry<Integer, String> entry = itr.next();
            System.out.println(entry.key +" - "+entry.value);
        }
    }

    private final int MAX2 = 65535;
    @Test
    public void testPutAndItrWithJDK() {
        // 和jdk的treemap比较
        AVLMap<Integer, String> avlMap = new AVLMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        for (int i = 0; i < MAX2; i++) {
            Integer key = random.nextInt(MAX2);
            String value = random.nextInt(MAX2) + "";
            avlMap.put(key, value);
            treeMap.put(key, value);
        }
        Assert.assertTrue(avlMap.size() == treeMap.size());
        System.out.println(avlMap.size());
        Iterator<Map.Entry<Integer, String>> itr1 = avlMap.iterator();
        Iterator<Map.Entry<Integer, String>> itr2 = treeMap.entrySet().iterator();
        while (itr1.hasNext() && itr2.hasNext()) {
            Assert.assertTrue(itr1.next().getKey().equals(itr2.next().getKey()));
        }
        Assert.assertTrue(!itr1.hasNext() && !itr2.hasNext());
    }

    @Test
    public void testQuery() {
        AVLMap<Integer, String> map = new AVLMap();
        map.put(4, "a");
        map.put(2, "b");
        map.put(6, "c");
        map.put(2, "d");
        map.put(1, "a");
        map.put(3, "d");
        map.put(5, "e");
        map.put(1, "f");
        Assert.assertTrue("a".equals(map.get(4)));
        Assert.assertTrue("f".equals(map.get(1)));
        Assert.assertTrue(null == map.get(7));
        Assert.assertTrue(map.containsKey(6));
        Assert.assertTrue(!map.containsKey(-1));
        Assert.assertTrue(map.containsValue("d"));
        Assert.assertTrue(!map.containsValue("xxxx"));
    }

    @Test
    public void testQueryWithJDK() {
        AVLMap<Integer, String> map1 = new AVLMap<>();
        TreeMap<Integer, String> map2 = new TreeMap<>();
        for (int i = 0; i < MAX2; i++) {
            int key = random.nextInt(MAX2);
            String value = random.nextInt(MAX2) + "";
            map1.put(key, value);
            map2.put(key, value);
        }
        for (int i = 0; i < MAX2; i++) {
            int key = random.nextInt(MAX2);
            Assert.assertTrue(map1.containsKey(key) == map2.containsKey(key));
            Assert.assertTrue(map1.get(key) == map2.get(key));
        }
        for (int i = 0; i < 255; i++) {
            String value = random.nextInt(MAX2) + "";
            Assert.assertTrue(map1.containsValue(value) == map2.containsValue(value));
        }
    }

    @Test
    public void testRemoveCase1() {
        AVLMap<Integer, String> map = new AVLMap<>();
        int[] array = {5,2,6,1,4,7,3};
        for (int key : array) {
            map.put(key, key+"");
        }
        System.out.println(map.remove(1));
        map.levelOrder();
        Iterator<AVLEntry<Integer, String>> itr = map.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next().key+" ");
        }
    }

    @Test
    public void testRemoveCase2() {
        AVLMap<Integer, String> map = new AVLMap<>();
        int[] array = {5,2,6,1,4,7,3};
        for (int key : array) {
            map.put(key, key+"");
        }
        System.out.println(map.remove(4));
        map.levelOrder();
        Iterator<AVLEntry<Integer, String>> itr = map.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next().key+" ");
        }
    }

    @Test
    public void testRemoveCase3() {
        AVLMap<Integer, String> map = new AVLMap<>();
        int[] array = {6,2,7,1,4,8,3,5};
        for (int key : array) {
            map.put(key, key+"");
        }
        System.out.println(map.remove(2));
        map.levelOrder();
        Iterator<AVLEntry<Integer, String>> itr = map.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next().key+" ");
        }
    }

    @Test
    public void testRemoveWithJDK() {
        AVLMap<Integer, String> map1 = new AVLMap<>();
        TreeMap<Integer, String> map2 = new TreeMap<>();
        for (int i = 0; i < MAX2; i++) {
            int key = random.nextInt(MAX2);
            String value = random.nextInt(MAX2)+"";
            map1.put(key, value);
            map2.put(key, value);
        }
        System.out.println(map1.size());
        for (int i = 0; i < MAX2 >> 1; i++) {
            int key = random.nextInt(MAX2);
            if (map1.containsKey(key)) {
                Assert.assertTrue(map1.remove(key).equals(map2.remove(key)));
            } else {
                Assert.assertTrue(map1.remove(key) == null && map2.remove(key) == null);
            }
        }
        System.out.println(map1.size());
        Assert.assertTrue(map1.size() == map2.size());
        Iterator<Map.Entry<Integer, String>> itr1 = map1.iterator();
        Iterator<Map.Entry<Integer, String>> itr2 = map2.entrySet().iterator();
        while (itr1.hasNext() && itr2.hasNext()) {
            Assert.assertTrue(itr1.next().getKey().equals(itr2.next().getKey()));
        }
        Assert.assertTrue(!itr1.hasNext() && !itr2.hasNext());
    }

    @Test
    public void testPerson() {
        AVLMap<Person, Integer> map = new AVLMap<Person, Integer>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.id - o2.id;
            }
        });
        for (int i = 0; i < MAX1; i++) {
            map.put(new Person(random.nextInt(MAX1), "name:"+random.nextInt(MAX1)), random.nextInt(MAX1));
        }
        Iterator<AVLEntry<Integer, String>> itr = map.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next().key+" ");
        }
    }
}

class Person {
    public int id;
    public String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
