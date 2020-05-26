package com.aaron.二叉搜索树1;

public class Test {
    public static void main(String[] args) {
        BiTree t = new BiTree(12);
        t.add(new BiTree(9));
        t.add(new BiTree(5));
        t.add(new BiTree(8));
        t.add(new BiTree(15));
        t.add(new BiTree(20));
        t.travel();// 采用中序遍历，则直接输出正确的顺序5，8，9，12，15，20
    }
}

class BiTree {
    private int data;
    private BiTree left;
    private BiTree right;

    public BiTree(int data) {
        this.data = data;
    }

    /**
     * 添加元素，元素跟自身做比较，如果小于，则放到左子树，左子树不为空，则递归左子树做add操作
     *                         如果大于，则放到右子树，右子树不为空，则递归右子树做add操作
     * @param x
     */
    public void add(BiTree x) {
        if (x.data < this.data) {
            if (this.left == null) {
                this.left = x;
            } else {
                left.add(x);
            }
        } else {
            if (this.right == null) {
                this.right = x;
            } else {
                this.right.add(x);
            }
        }
    }

    /**
     * 中序遍历：就是先travel遍历左子树，再输出自己，在travel遍历右子树
     */
    public void travel() {
        if (this.left != null) {
            this.left.travel();
        }
        System.out.println(data);
        if (this.right != null) {
            this.right.travel();
        }
    }
}