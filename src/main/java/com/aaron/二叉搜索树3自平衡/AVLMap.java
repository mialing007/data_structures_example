package com.aaron.二叉搜索树3自平衡;

import org.junit.Assert;

import java.util.*;

public class AVLMap<K, V> implements Iterable{
    private int size;
    private AVLEntry<K, V> root;
    private Comparator<K> comparator;

    // 用于记录插入过程走过的路径，
    private Stack<AVLEntry<K, V>> stack = new Stack<>();

    public AVLMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public AVLMap() {
    }

    public V put(K key, V value) {
        if (root == null) {
            // 树为空时候，直接将该节点赋值给根节点；
            root = new AVLEntry<>(key, value);
            stack.push(root);
            size++;
        } else {
            // 不断比较
            AVLEntry<K, V> p = root;
            while (p != null) {
                // 走过的路径压栈，用于之后调整平衡fixAfterInsertion
                stack.push(p);
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
                    // 需要先清空栈，这里不需要旋转调整了，防止影响下次
                    stack.clear();
                    return p.setValue(value);
                }

            }
        }
        fixAfterInsertion(key);
        return null;
    }

    /**
     * 校验是否平衡
     */
    public void checkBalance() {
        postOrderCheckBalance(root);
    }

    /**
     * 后续遍历
     */
    private void postOrderCheckBalance(AVLEntry<K, V> p) {
        while (p != null) {
            postOrderCheckBalance(p.left);
            postOrderCheckBalance(p.right);
            Assert.assertTrue(Math.abs(getHeight(p.left) - getHeight(p.right)) <= 1);
        }
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
            p = fixAfterDeletion(p);
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

    /**
     * 获取以p为根的树高
     * @param p
     * @return
     */
    public int getHeight(AVLEntry<K, V> p) {
        return p == null ? 0 : p.height;
    }

    /**
     * 左旋
     * @param p
     * @return
     */
    private AVLEntry<K, V> rotateRight(AVLEntry<K, V> p) {
        AVLEntry<K, V> left = p.left;
        p.left = left.right;
        left.right = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        left.height = Math.max(getHeight(left.left), p.height) + 1;
        return left;
    }

    /**
     * 右旋
     * @param p
     * @return
     */
    private AVLEntry<K, V> rotateLeft(AVLEntry<K, V> p) {
        AVLEntry<K, V> right = p.right;
        p.right = right.left;
        right.left = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        right.height = Math.max(p.height, getHeight(right.right)) + 1;
        return right;
    }

    /**
     * 先左旋，再右旋转
     * @param p
     * @return
     */
    private AVLEntry<K, V> firstLeftThenRight(AVLEntry<K, V> p) {
        // 先对左子树进行左旋
        AVLEntry<K, V> left = rotateLeft(p.left);
        p.left = left;
        // 再对该节点进行右旋转
        return rotateRight(p);
    }
    /**
     * 先右旋，再左旋转
     * @param p
     * @return
     */
    private AVLEntry<K, V> firstRightThenLeft(AVLEntry<K, V> p) {
        // 先对右子树进行右旋转
        AVLEntry<K, V> right = rotateRight(p.right);
        p.right = right;
        // 再对该节点进行左旋转
        return rotateLeft(p);
    }

    /**
     * 插入之后，平衡
     * 插入时候，会记录走过的路径（比较的节点），压入栈中。
     * @param key
     */
    private void fixAfterInsertion(K key) {
        AVLEntry<K, V> p = root;
        while (!stack.isEmpty()) {
            p = stack.pop();
            p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
            int d = getHeight(p.left) - getHeight(p.right);
            if (Math.abs(d) <= 1) {
                // 说明已经平衡，继续向上回溯
                continue;
            } else {
                if (d == 2) {
                    if (compare(key, p.left.key) < 0) {
                        // 左左的情况
                        p = rotateRight(p);
                    } else {
                        // 左右的情况,需要双旋转，先左旋再右旋转
                        p = firstLeftThenRight(p);
                    }
                } else {
                    // -2的情况
                    if (compare(key, p.right.key) > 0) {
                        // 右右的情况
                        p = rotateLeft(p);
                    } else {
                        // 右左的情况,需要双旋转，先右旋再左旋转
                        p = firstRightThenLeft(p);
                    }
                }
                if (!stack.isEmpty()) {
                    if (compare(key, stack.peek().key) < 0) {
                        stack.peek().left = p;
                    } else {
                        stack.peek().right = p;
                    }
                }
            }
        }
        root = p;
    }

    /**
     *
     */
    private AVLEntry<K, V> fixAfterDeletion(AVLEntry<K, V> p) {
        if (p == null) {
            return null;
        } else {
            p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
            int d = getHeight(p.left) - getHeight(p.right);
            // d=2树不平衡
            if (d == 2) {
                if (getHeight(p.left.left) - getHeight(p.left.right) >= 0) {
                    // 左左的情况，
                    p = rotateRight(p);
                } else {
                    p = firstLeftThenRight(p);
                }
            } else if (d == -2) {
                if (getHeight(p.right.left) - getHeight(p.right.right) <= 0) {
                    // 右右的情况，
                    p = rotateLeft(p);
                } else {
                    p = firstRightThenLeft(p);
                }
            }
        }
        return p;
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
