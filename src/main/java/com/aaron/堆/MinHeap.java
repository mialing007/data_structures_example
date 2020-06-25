package com.aaron.堆;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 小顶堆
 */
public class MinHeap<E> {
    /**
     * 存储堆数据的数组
     */
    private Object[] data;

    /**
     * 堆中元素的个数
     */
    private int size;

    private final Comparator<? super E> comparator;

    public MinHeap() {
        this(11, null);
    }

    public MinHeap(int initialCapacity) {
        this(initialCapacity, null);
    }

    public MinHeap(int initialCapacity, Comparator<? super E> comparator) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException();
        }
        this.data = new Object[initialCapacity];    // 初始化数组
        this.comparator = comparator;   // 初始化一个比较器
    }

    public int size() {
        return size;
    }

    public E peek() {
        return size == 0 ? null : (E) data[0];
    }

    public boolean offer(E e) {
        int i = size;
        if (i >= data.length) {
            // 如果数组元素满了，则需要扩容
            grow(i + 1);
        }
        size = i + 1;
        if (i == 0) {
            // 插入第一个元素时，直接将该值赋值给堆顶元素
            data[0] = e;
        } else {
            // 筛选，就是将插入，并且按住堆的要求
            siftUp(i, e);
        }
        return true;
    }
    /**
     * 筛选
     * @param k 代表要插入元素的下标，先加到最后一位，然后对其进行调整比较
     * @param x
     */
    private void siftUp(int k, E x) {
        if (comparator != null) {
            // 如果比较器不为空，则通过比较器方式
            siftUpUsingComparator(k, x);
        } else {
            // 如果比较器为空，则需要对象本身继承Comparable接口
            siftUpComparable(k, x);
        }
    }

    /**
     * 比较器筛选节点x
     * @param k 调整的节点下标，插入时，k是最后一位，所以下标为最后一位下标
     * @param x 节点x
     */
    private void siftUpUsingComparator(int k, E x) {
        while (k > 0) {
            // 将指定元素放到堆的最后一个，并且求出其父节点
            int parent = (k - 1) >>> 1;
            Object parentElement = data[parent];
            if (comparator.compare(x, (E) parentElement) >= 0) {
                // 如果大于等于其父节点，则直接终止循环，将该值x赋值给data[k]
                break;
            }
            // 小于其父亲节点，则将父节点值赋值给该节点。
            data[k] = parentElement;
            // k下标设置为父节点下标，继续循环对父节点筛选
            k = parent;
        }
        // 最后确定好节点所处位置，将x赋值给data[k]
        data[k] = x;
    }

    /**
     * 对象实现Comparabe接口的筛选
     * @param k 调整的节点下标，插入时，k是最后一位，所以下标为最后一位下标
     * @param x
     */
    private void siftUpComparable(int k, E x) {
        Comparable<? super E> key = (Comparable<? super E>) x;
        while (k > 0) {
            // 将该元素放到堆的最后一个，并且求出其父节点下标 = (k-1)/2
            int parent = (k - 1) >>> 1;
            // 父节点
            Object parentElement = data[parent];
            if (key.compareTo((E) parentElement) >= 0) {
                // 如果大于等于其父亲节点
                break;
            }
            // 小于其父亲节点，则将副节点值赋值给该节点
            data[k] = parentElement;
            // k下标设置为父节点的下标，继续循环对父节点筛选
            k = parent;
        }
        // 最后确定好该节点位置k，将x赋值给data[k]
        data[k] = x;
    }

    public E poll() {
        if (size == 0) {
            return null;
        }
        int s = --size;
        // 获取queue列头元素， 作为方法返回值
        E result = (E) data[0];
        // 获取队列最后一个元素
        E x = (E) data[s];
        // 将最后一个元素置为空，并将该元素放到队列头，开始向下筛选
        data[s] = null;
        if (s != 0) {
            siftDown(0, x);
        }
        return result;
    }

    /**
     * 向下筛选的两种方法：如果比较器comparator不为空，则使用比较器，如果为空则要求对象实现Comparable接口
     * @param k 弹出元素时， 该k为队列头元素，就是0，
     * @param x 队列的最后一个元素，将队列最后一个元素放置堆顶，开始向下筛选
     */
    private void siftDown(int k, E x) {
        if (comparator != null) {
            siftDownUsingComparator(k, x);
        } else {
            siftDownComparable(k, x);
        }
    }

    /**
     * 队列弹出堆顶元素，将最后一个元素赋值给堆顶，从堆顶开始向下筛选(比较器的方式)
     * @param k
     * @param x
     */
    private void siftDownUsingComparator(int k, E x) {
        int half = size >>> 1;
        while (k < half) {
            // 获取其左孩子，左孩子下标为(k * 2)+1,  右孩子下标为（k * 2）+2
            int child = (k << 1) + 1;
            // 获取左孩子的值
            Object c = data[child];
            int right = child + 1;
            // right < size说明存在右孩子，则比较左右孩子的大小
            if (right < size && comparator.compare((E) c, (E) data[right]) > 0) {
                // 如果右孩子小于左孩子，则取右孩子。
                c = data[child = right];
            }
            // 将较小的孩子与该节点做比较，如果该节点不大于较小孩子，则停止循环
            if (comparator.compare(x, (E) c) <= 0) {
                break;
            }
            // 如果该节点大于较小的孩子，则用较小孩子替换data[k]
            data[k] = c;
            // 将较小孩子的下标赋值给k，继续循环，向下筛选
            k = child;
        }
        // 最后确定k的位置，将x赋值给data[k]
        data[k] = x;
    }

    /**
     * 队列弹出堆顶元素，将最后一个元素赋值给堆顶，从堆顶开始向下筛选(对象实现Comparable接口的方式)
     * @param k
     * @param x
     */
    private void siftDownComparable(int k, E x) {
        Comparable<? super E> key = (Comparable<? super E>)x;
        int half = size >>> 1;
        while (k < half) {
            // 获取其左孩子，左孩子下标为(k * 2)+1,  右孩子下标为（k * 2）+2
            int child = (k << 1) + 1;
            // 获取左孩子的值
            Object c = data[child];
            int right = child + 1;
            // right < size说明存在右孩子，则比较左右孩子的大小
            if (right < size && ((Comparable<? super E>) c).compareTo((E) data[right]) > 0) {
                // 如果右孩子小于左孩子，则取右孩子。
                c = data[child = right];
            }
            // 将较小的孩子与该节点做比较，如果该节点不大于较小孩子，则停止循环
            if (key.compareTo((E) c) <= 0) {
                break;
            }
            // 如果该节点大于较小的孩子，则用较小孩子替换data[k]
            data[k] = c;
            // 将较小孩子的下标赋值给k，继续循环，向下筛选
            k = child;
        }
        // 最后确定k的位置，将x赋值给data[k]
        data[k] = x;
    }

    private void grow(int minCapacity) {
        int oldCapacity = data.length;
        // 如果小的元素小于64，则扩容两倍+2， 否则，则扩容50%
        int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                (oldCapacity + 2) :
                (oldCapacity >> 1));
        // overflow-conscious code
        if (newCapacity - Integer.MAX_VALUE - 8 > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        data = Arrays.copyOf(data, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        return (minCapacity > Integer.MAX_VALUE - 8) ?
                Integer.MAX_VALUE :
                Integer.MAX_VALUE - 8;
    }

    // 测试方法
    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        // 模拟测试小顶堆
        minHeap.offer(4);
        minHeap.offer(2);
        minHeap.offer(5);
        minHeap.offer(8);
        minHeap.offer(9);
        minHeap.offer(0);

        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());

        // 模拟测试大顶堆
        minHeap.offer(new People("9岁", 9));
        minHeap.offer(new People("1岁", 1));
        minHeap.offer(new People("3岁", 3));
        minHeap.offer(new People("7岁", 7));
        minHeap.offer(new People("8岁", 8));
        minHeap.offer(new People("5岁", 5));
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());

        // 模拟测试comparator
        MinHeap heap = new MinHeap(11, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (!(o1 instanceof People && o1 instanceof People)) {
                    return 0;
                }
                return ((People)o1).age - ((People)o2).age;
            }
        });
        heap.offer(new People("9岁", 9));
        heap.offer(new People("1岁", 1));
        heap.offer(new People("3岁", 3));
        heap.offer(new People("7岁", 7));
        heap.offer(new People("8岁", 8));
        heap.offer(new People("5岁", 5));
        System.out.println(heap.poll());
        System.out.println(heap.poll());
        System.out.println(heap.poll());
        System.out.println(heap.peek());
        System.out.println(heap.poll());
        System.out.println(heap.poll());
        System.out.println(heap.poll());
        System.out.println(heap.poll());
        System.out.println(heap.poll());


        KthLargest kthLargest = new KthLargest(3, new int[]{4,5,8,2});
        System.out.println(kthLargest.add(3));
        System.out.println(kthLargest.add(5));
        System.out.println(kthLargest.add(10));
        System.out.println(kthLargest.add(9));
        System.out.println(kthLargest.add(4));

    }

    // 测试类
    static class People implements Comparable{
        private String name;
        private int age;
        public People(String name, int age) {
            this.name = name;
            this.age = age;
        }
        @Override
        public int compareTo(Object o) {
            People p = (People) o;
            return p.age - this.age;
        }

        @Override
        public String toString() {
            return "People{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}

class KthLargest {
    MinHeap<Integer> minHeap;
    int k;
    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.minHeap = new MinHeap(k);
        for (int n : nums) {
            minHeap.offer(n);
        }
    }

    public int add(int val) {
        if (minHeap.size() < k) {
            minHeap.offer(val);
        } else if (minHeap.peek() < val) {
            minHeap.poll();
            minHeap.offer(val);
        }
        return minHeap.peek();
    }
}