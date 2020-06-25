package com.aaron.二叉搜索树4红黑树;

import com.aaron.二叉搜索树3自平衡.AVLMap;
import org.junit.Test;

import java.util.Random;
import java.util.TreeMap;

/**
 * 测试AVL自平衡二叉树和红黑树性能
 * 插入性能：红黑树略高于AVL树 （由于）
 * 查询性能：AVL树略高于红黑树 （由于红黑树极端情况下左子树高度）
 */
public class TestAVLVsRBT {
    private Random random=new Random();
    private static int Max=(1<<21)+99999;
    private static AVLMap<Integer, Integer> map1=new AVLMap<Integer, Integer>();
    private static TreeMap<Integer, Integer> map2=new TreeMap<Integer, Integer>();

    /**
     * 测试AVL插入性能，查看插入耗时
     */
    @Test
    public void _1_TestAVLInsert(){
        for(int i=0;i<Max;i++){
            map1.put(random.nextInt(Max), random.nextInt(Max));
        }
    }

    /**
     * 测试红黑树插入性能，查看插入耗时
     */
    @Test
    public void _1_TestRBTInsert() throws Throwable{
        for(int i=0;i<Max;i++){
            map2.put(random.nextInt(Max), random.nextInt(Max));
        }
    }

    /**
     * 测试AVL树的查询性能，查看查询耗时
     */
    @Test
    public void _2_TestAVLSearch(){
        for(int i=0;i<Max<<1;i++){
            map1.containsKey(random.nextInt(Max));
        }
    }

    /**
     * 测试红黑树的查询性能，查看查询耗时
     */
    @Test
    public void _2_TestRBTSearch(){
        for(int i=0;i<Max<<1;i++){
            map2.containsKey(random.nextInt(Max));
        }
    }

    /**
     * 测试AVL树的删除性能，查看删除耗时
     */
    @Test
    public void _3_TestAVLRemove(){
        for(int i=0;i<Max>>>1;i++){
            map1.remove(random.nextInt(Max));
        }
    }
    /**
     * 测试红黑树的删除性能，查看删除耗时
     */
    @Test
    public void _3_TestRBTRemove(){
        for(int i=0;i<Max>>>1;i++){
            map2.remove(random.nextInt(Max));
        }
    }
}
