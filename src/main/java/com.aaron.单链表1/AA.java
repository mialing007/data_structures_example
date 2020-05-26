package com.aaron.单链表1;

public class AA {
    public static void main(String[] args) {
        MyList head = new MyList(10);
        head.append(new MyList(30));
        head.append(new MyList(40));
        head.append(new MyList(50));
        head.add(new MyList(10));
        head.show();
    }
}

class MyList {
    private int data;
    private MyList next;
    public MyList(int x) {
        data = x;
    }

    /**
     * 插入元素，在指定的元素后插入元素
     * @param x
     */
    public void add(MyList x) {
        x.next = next;
        next = x;
    }

    /**
     * 追加元素，添加到尾部，找到最后一个元素添加
     * @param x 添加元素
     */
    public void append(MyList x) {
        MyList p = this;
        while (p.next != null) {
            p = p.next;
        }
        p.next = x;
    }

    /**
     * 显示所有元素，从当前元素遍历所有元素
     */
    public void show() {
        MyList p = this;
        while (p != null) {
            System.out.println(p.data);
            p = p.next;
        }
    }
}
