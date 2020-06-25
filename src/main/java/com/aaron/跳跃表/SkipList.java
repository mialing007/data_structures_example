package com.aaron.跳跃表;

import java.util.Random;

public class SkipList<T> {
    private SkipListNode<T> head, tail;// 这两个值指向最高层头部，尾部
    private int size;//节点总数
    private int topLevel;// 最高层的层数，最底层从0开始
    private Random random;//用于投掷硬币
    private static final double PROBABILITY=0.5;//向上提升一个的概率

    public SkipList() {
        random = new Random();
        clear();
    }

    public void clear() {
        head = new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
        tail = new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
        horizontalLink(head, tail);
        topLevel = 0;
        size = 0;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public int size() {
        return size;
    }

    /**
     * 在最下面一层，找到要插入的位置前面的那个节点（或者相同的节点）
     * @param key
     * @return
     */
    private SkipListNode<T> findPrevNodeInBottom(int key) {
        // 从最上层链向下一直查找到最底层链
        SkipListNode<T> p = head;
        while (true) {
            while (p.right.key != SkipListNode.TAIL_KEY && p.right.key <= key) {
                p = p.right;
            }
            // 向下找下一个链，直到最后一个链0，
            if (p.down != null) {
                p = p.down;
            } else {
                break;
            }
        }
        return p;
    }

    public SkipListNode<T> search(int key) {
        SkipListNode<T> p = findPrevNodeInBottom(key);
        if (key == p.getKey()) {
            return p;
        } else {
            return null;
        }
    }

    public void put(int k, T v) {
        SkipListNode<T> p = findPrevNodeInBottom(k);
        // 如果key值相同，替换原来的value即可结束
        if (k == p.getKey()) {
            p.value = v;
            return;
        }
        SkipListNode<T> q = new SkipListNode<T>(k, v);
        // 将新元素插入到链表
        backLink(p, q);
        int currentLevel = 0;// 当前所处于的层数
        // 抛硬币
        while (random.nextDouble() < PROBABILITY) {
            // 如果超出高度，需要重新建一个顶层
            if (currentLevel >= topLevel) {
                // 层数上升一层，创建头和尾巴节点，与下层的头尾上下相连
                topLevel++;
                SkipListNode<T> p1 = new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
                SkipListNode<T> p2 = new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
                horizontalLink(p1, p2);
                vertiacalLink(p1, head);
                vertiacalLink(p2, tail);
                head = p1;
                tail = p2;
            }
            // 将p移动到上一层
            while (p.up == null) {
                p = p.left;
            }
            p = p.up;

            // 将新元素插入到上一层
            SkipListNode<T> e = new SkipListNode<T>(k, null);//只保存key就ok
            backLink(p, e);// 将e插入到p的后边
            vertiacalLink(e, q);// 将e和q上下连接
            q = e;
            currentLevel++;
        }
        size++;
    }

    public T deleteNode(int key) {
        SkipListNode<T> headForLevel = head;
        SkipListNode<T> findNode = null;
        // 第一层一直向下找，如果某一层存在p，则停止循环
        while (true) {
            if (headForLevel == null) {
                // 找到了最后一行仍没有找到该节点，说明节点不存在，直接返回空
                return null;
            }
            SkipListNode<T> p = headForLevel;
            while (p.key != SkipListNode.TAIL_KEY && p.key != key) {
                p = p.right;
            }
            if (p.key == key) {
                // 找到了p，则停止，
                findNode = p;
                break;
            } else {
                headForLevel = headForLevel.down;
            }
        }

        T value = null;
        // 找到了该节点，则从该条链到底部的链都删除该节点
        while (true) {
            if (findNode  == null) {
                // 为空，说明上层循环已经是最后一层了
                break;
            }
            // 将该节点的前后两个节点左右相连,从链中删除
            horizontalLink(findNode.left, findNode.right);
            // value值，
            value = findNode.value;
            // 继续向下，直到最后一个链
            findNode = findNode.down;
        }

        return value;
    }

    /**
     * node1后边增加node2
     * @param node1
     * @param node2
     */
    private void backLink(SkipListNode<T> node1,SkipListNode<T> node2) {
        node2.left = node1;
        node2.right = node1.right;
        node1.right.left = node2;
        node1.right = node2;
    }

    /**
     * 水平双向连接
     * @param node1
     * @param node2
     */
    private void horizontalLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node1.right = node2;
        node2.left = node1;
    }

    /**
     * 垂直双向连接
     * @param node1
     * @param node2
     */
    private void vertiacalLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node1.down = node2;
        node2.up = node1;
    }

    /**
     * 打印出原始数据，最下边一层数据
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "跳表为空";
        }
        StringBuilder builder = new StringBuilder();
        SkipListNode<T> p = head;
        while (p.down != null) {
            p = p.down;
        }

        if (p.right != null) {
            p = p.right;
        }
        while (p.right != null) {
            builder.append(p);
            builder.append("\n");
            p = p.right;
        }
        return builder.toString();
    }

    /**
     * 层序打印跳表
     */
    public void printSkipByLevel() {
        SkipListNode<T> headForLevel = head;
        while (true) {
            SkipListNode<T> p = headForLevel;
            if (p == null) {
                break;
            }
            while (p.key != SkipListNode.TAIL_KEY) {
                System.out.print("{k:"+p.key+", v:"+p.value+"}");
                p = p.right;
            }
            headForLevel = headForLevel.down;
            System.out.println();
        }
    }
}
