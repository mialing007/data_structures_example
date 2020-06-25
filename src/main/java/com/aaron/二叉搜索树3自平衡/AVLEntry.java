package com.aaron.二叉搜索树3自平衡;

import java.util.Map;

public class AVLEntry<K, V> implements Map.Entry<K, V> {
    K key;
    V value;
    AVLEntry<K, V> left;
    AVLEntry<K, V> right;

    // 自平衡二叉树，记录树高，默认为1
    int height = 1;

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public AVLEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public AVLEntry(K key, V value, AVLEntry<K, V> left, AVLEntry<K, V> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "AVLEntry{" +
                "key=" + key +
                ", value=" + value +
                ", height=" + height +
                '}';
    }
}
