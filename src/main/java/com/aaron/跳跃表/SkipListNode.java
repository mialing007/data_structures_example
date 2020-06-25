package com.aaron.跳跃表;

/**
 * 跳跃表的节点，包括key-value和上下左右四个指针
 * created by junqi.liu
 * @param <T>
 */
public class SkipListNode<T> {
    public int key;
    public T value;
    // 该节点的上，下，左，右节点
    public SkipListNode<T> up, down, left, right;

    // 最小值
    public static final int HEAD_KEY = Integer.MIN_VALUE;
    // 最大值
    public static final int TAIL_KEY = Integer.MAX_VALUE;
    public SkipListNode(int k, T v) {
        key = k;
        value = v;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
