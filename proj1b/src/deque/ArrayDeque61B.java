package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private int decIndex(int idx)  { return (idx - 1 + capacity()) % capacity(); }
    private int incIndex(int idx)  { return (idx + 1) % capacity(); }

    public int capacity() {
        return items.length;
    }

    private void resize(int newCapacity) {
        // 1. 新建更大的底层数组
        T[] newItems = (T[]) new Object[newCapacity];

        // 2. 选择一个“起始位置” start，把现有的 size 个元素按逻辑顺序搬过去
        //    你可以把它设为 0（全部挪到最前面），也可以设成 newCapacity - size（放到末尾），
        //    或者 (newCapacity - size) / 2（放在中间），任何保证 [start, start+size) 有足够空间都行。
        //int start = 0;
        int start = ((nextFirst + 1) + (newCapacity - capacity())) % newCapacity;

        // 3. 物理下标 oldIndex 从 nextFirst + 1 开始，循环 wrap 取出每个元素
        int oldIndex = (nextFirst + 1) % capacity();
        for (int i = 0; i < size; i++) {
            newItems[(start + i) % newCapacity] = items[oldIndex];
            oldIndex = (oldIndex + 1) % capacity();
        }

        // 4. 更新 nextFirst/nextLast
        //    nextFirst 应指向搬好数据区段的前一个空位
        //    nextLast  应指向下一个 addLast 要插入的位置，也就是 start+size
        nextFirst = (start - 1 + newCapacity) % newCapacity;
        nextLast  = (start + size) % newCapacity;

        // 5. 切换到底层数组
        items = newItems;
    }


    private void resizeAndRelocateAtFront(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        // 按逻辑顺序搬运
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        // 重置指针：nextFirst 指向最前面“空槽”的前一个，nextLast 指向末尾元素后的那个空槽
        nextFirst = newCapacity - 1;
        nextLast  = size;
        items     = newItems;
    }


    @Override
    public void addFirst(T x) {
        if (size == capacity()) {
            resize(capacity() * 2);
        }
        items[nextFirst] = x;
        size++;
        nextFirst = decIndex(nextFirst);
    }

    @Override
    public void addLast(T x) {
        if (size == capacity()) {
            resize(capacity()* 2);
        }
        items[nextLast] = x;
        size++;
        nextLast = incIndex(nextLast);
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i=0; i<size; i++) {
            returnList.add(get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size <= capacity() * 0.25) {
            resize(capacity() / 2);
        }
        if (size == 0) {
            return null;
        }
        int firstIndex = incIndex(nextFirst);
        T firstItem = items[firstIndex];
        items[firstIndex] = null;
        nextFirst = incIndex(nextFirst);
        size -= 1;
        return firstItem;
    }

    @Override
    public T removeLast() {
        if (size <= capacity() * 0.25) {
            resize(capacity() / 2);
        }
        if (size == 0) {
            return null;
        }
        int lastIndex = decIndex(nextLast);
        T lastItem = items[lastIndex];
        items[lastIndex] = null;
        nextLast = decIndex(nextLast);
        size -= 1;
        return lastItem;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size) {
            return null;
        }
        int realIndex = nextFirst + 1 + index;
        int realIndexMod = Math.floorMod(realIndex + capacity(), capacity());
        return items[realIndexMod];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }


    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDequeIterator() {
            wizPos = (nextFirst + 1) % capacity();
        }

        @Override
        public boolean hasNext() {
            return wizPos != nextLast;
        }

        @Override
        public T next() {
            T returnItem = items[wizPos];
            wizPos = (wizPos + 1) % capacity();
            return returnItem;
        }
    }

    public static void main(String[] args) {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);

//        Iterator<Integer> ad_seer = ad.iterator();
//        while(ad_seer.hasNext()) {
//            int i = ad_seer.next();
//            System.out.println(i);
//        }

        for (int i : ad) {
            System.out.println(i);
        }
    }

}
