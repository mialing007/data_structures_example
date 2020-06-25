package com.aaron.二叉搜索树3自平衡;

import java.util.Iterator;
import java.util.Stack;

/**
 * 通过栈的方式，中序遍历
 * @param <K>
 * @param <V>
 */
public class AVLIterator<K, V> implements Iterator {
    private Stack<AVLEntry<K, V>> stack;

    public AVLIterator(AVLEntry<K, V> root) {
        stack = new Stack<AVLEntry<K, V>>();
        addLeftPath(root);
    }

    private void addLeftPath(AVLEntry<K, V> p) {
        while (p != null) {
            stack.push(p);
            p = p.left;
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Object next() {
        // 弹出栈顶元素
        AVLEntry<K, V> currentNode = stack.pop();
        if (currentNode.right != null) {
            // 如果有右子树，将右子树压栈，右子树要先于其父节点. 右子树执行方法
            addLeftPath(currentNode.right);
        }
        return currentNode;
    }
}
