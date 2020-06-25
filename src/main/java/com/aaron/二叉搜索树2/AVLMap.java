package com.aaron.二叉搜索树2;

import javax.swing.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class AVLMap<K, V> implements Iterable{
    private int size;
    private AVLEntry<K, V> root;
    private Comparator<K> comparator;

    public AVLMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public AVLMap() {
    }

    public V put(K key, V value) {
        if (root == null) {
            // 树为空时候，直接将该节点赋值给根节点；
            root = new AVLEntry<>(key, value);
            size++;
        } else {
            // 不断比较
            AVLEntry<K, V> p = root;
            while (p != null) {
                int compareResult = compare(key, p.key);
                if (compareResult < 0) {
                    // 判断左子树是否为空
                    if (p.left == null) {
                        // 将该值赋值给左子树
                        p.left = new AVLEntry<>(key, value);
                        size++;
                        break;
                    } else {
                        // 否则继续循环，将p指向left
                        p = p.left;
                    }
                } else if (compareResult > 0) {
                    // 判断右子树是否为空
                    if (p.right == null) {
                        // 将该值赋值给左子树
                        p.right = new AVLEntry<>(key, value);
                        size++;
                        break;
                    } else {
                        p = p.right;
                    }
                } else {
                    // 等于0时，说明找到了重复的节点
                    return p.setValue(value);
                }

            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V get(K key) {
        AVLEntry<K, V> entry = getEntry(key);
        return entry != null ? entry.getValue() : null;
    }

    public boolean containsKey(K key) {
        return getEntry(key) != null;
    }

    public boolean containsValue(V value) {
        Iterator<AVLEntry<K, V>> itr = new AVLIterator<>(root);
        while (itr.hasNext()) {
            if (itr.next().getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public AVLEntry<K, V> getFirstEntry(AVLEntry<K, V> p) {
        if (p == null) {
            return null;
        }
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    public AVLEntry<K, V> getLastEntry(AVLEntry<K, V> p) {
        if (p == null) {
            return null;
        }
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    /**
     * 删除对应的key
     * @param key
     * @return
     */
    public V remove(K key) {
        AVLEntry<K, V> entry = getEntry(key);
        if (entry == null) {
            return null;
        }

        V oldValue = entry.getValue();
        // 需要重新赋值给root，有可能删除的是root节点
        root = deleteEntry(root, key);
        size--;
        return oldValue;
    }

    public void levelOrder() {
        Queue<AVLEntry<K, V>> queue = new LinkedList<>();
        queue.add(root);
        int preCount = 1;
        int pCount = 0;
        while (!queue.isEmpty()) {
            AVLEntry<K, V> entry = queue.poll();
            System.out.print(entry + " ");
            preCount--;
            if (entry.left != null) {
                queue.add(entry.left);
                pCount++;
            }
            if (entry.right != null) {
                queue.add(entry.right);
                pCount++;
            }
            if (preCount == 0) {
                preCount = pCount;
                pCount = 0;
                System.out.println();
            }
        }
    }

    /**
     * 删除以p为根的子树中key为key的节点
     * @param p 树根
     * @param key 要删除节点key
     * @return 返回删除的节点
     */
    private AVLEntry<K, V> deleteEntry(AVLEntry<K, V> p, K key) {
        if (p == null) {
            return null;
        } else {
            int compareResult = compare(key, p.key);
            if (compareResult == 0) {
                // 查找到对应节点
                // 分三种情况
                if (p.left == null && p.right == null) {
                    // 为叶子节点，则直接删除，把p置为空，也就
                    p = null;
                } else if (p.left != null && p.right == null) {
                    // 只有左孩子，将p指向左孩子
                    p = p.left;
                } else if (p.left == null && p.right != null) {
                    // 只有右孩子，将p指向右孩子
                    p = p.right;
                } else {
                    // 两个孩子都存在,随机取左孩子或右孩子来替换该节点，
                    // 这里size&1就是判断奇数，偶数，没有实际意义，为了随机取左右孩子
                    if ((size&1)==0) {
                        // 获取右孩子中最小值，
                        AVLEntry<K, V> rightMin = getFirstEntry(p.right);
                        p.key = rightMin.key;
                        p.value = rightMin.value;
                        AVLEntry<K, V> newRight = deleteEntry(p.right, p.key);
                        p.right = newRight;
                    } else {
                        // 获取左孩子中最大值
                        AVLEntry<K, V> leftMax = getLastEntry(p.left);
                        p.key = leftMax.key;
                        p.value = leftMax.value;
                        AVLEntry<K, V> newLeft = deleteEntry(p.left, p.key);
                        p.left = newLeft;
                    }
                }
            } else if (compareResult < 0) {
                AVLEntry<K, V> newLeft = deleteEntry(p.left, key);
                p.left = newLeft;
            } else if (compareResult > 0) {
                AVLEntry<K, V> newRight = deleteEntry(p.right, key);
                p.right = newRight;
            }
            return p;
        }
    }

    private AVLEntry<K, V> getEntry(K key) {
        AVLEntry<K, V> p = root;
        while (p != null) {
            int compareResult = compare(key, p.key);
            if (compareResult == 0) {
                // 找到了，返回
                return p;
            } else if (compareResult < 0) {
                // 指向左子树，继续循环
                p = p.left;
            } else {
                // 指向右子树，继续循环
                p = p.right;
            }
        }
        // 为找到，返回null
        return null;
    }

    private int compare(K a, K b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            return ((Comparable<K>) a).compareTo(b);
        }
    }

    @Override
    public Iterator iterator() {
        return new AVLIterator(root);
    }
}
