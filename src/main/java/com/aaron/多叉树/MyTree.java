package com.aaron.多叉树;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyTree {
    private List<Node> tree = new ArrayList<Node>();
    class Node {
        private String data;
        private String parent;

        public Node(String parent, String data) {
            this.data = data;
            this.parent = parent;
        }
    }
    public void add(String parent, String data) {
        Node node = new Node(parent, data);
        tree.add(node);
    }

    public String getParent(String data) {
        for (int i = 0; i < tree.size(); i++) {
            Node node = tree.get(i);
            if (node.data.equals(data)) {
                return node.parent;
            }
        }
        return null;
    }

    public List<String> getChild(String data) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < tree.size(); i++) {
            Node node = tree.get(i);
            if (node.parent.equals(data)) {
                list.add(node.data);
            }
        }
        return list;
    }

    public static void main(String[] args) {
//        MyTree tree = new MyTree();
//        tree.add("世界", "亚洲");
//        tree.add("世界", "美洲");
//        tree.add("世界", "欧洲");
//        tree.add("世界", "非州");
//        tree.add("亚洲", "日本");
//        tree.add("亚洲", "中国");
//        tree.add("亚洲", "韩国");
//        tree.add("中国", "河南");
//        tree.add("中国", "海南");
//        tree.add("中国", "上海");
//
//        System.out.println(tree.getParent("河南"));
//        System.out.println("-------------------------");
//        System.out.println(tree.getChild("世界"));

        List l = new ArrayList();
        l.add(1);
        l.add("2");
        l.add(new MyTree());
        System.out.println(l);
        Object a[] = new Object[2];
        a[1] = 1;
        System.out.println(a[1].getClass());

        int b[] = {1,2,3,4,5};
        int[] c = Arrays.copyOf(b, 10);
        System.out.println(b.length);
        System.out.println(c.length);
        System.out.println(c);

        System.arraycopy(b, 2, b, 1, 3);
        System.out.println(c);
    }
}
