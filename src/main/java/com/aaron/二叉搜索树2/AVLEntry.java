package com.aaron.二叉搜索树2;

import java.util.Map;

public class AVLEntry<K, V> implements Map.Entry<K, V> {
    K key;
    V value;
    AVLEntry<K, V> left;
    AVLEntry<K, V> right;


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
                '}';
    }
}
