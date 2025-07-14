package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private class Node{
        public Node prev;
        public T item;
        public Node next;
        public Node(Node p, T i, Node n){
            prev = p;
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B(){
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }


    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node newNode = new Node(sentinel, x, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node newNode = new Node(sentinel.prev, x, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node temp = sentinel.next;
        while(temp != sentinel){
            returnList.add(temp.item);
            temp = temp.next;
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node firstNode = sentinel.next;
        // unlink it
        sentinel.next = firstNode.next;
        firstNode.next.prev = sentinel;
        // grab the item
        T item = firstNode.item;
        // fully detach the node (helps GC)
        firstNode.next = null;
        firstNode.prev = null;
        // update size
        size--;
        return item;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node lastNode = sentinel.prev;
        // unlink it
        sentinel.prev = lastNode.prev;
        lastNode.prev.next = sentinel;
        // grab the item
        T item = lastNode.item;
        // fully detach the node (helps GC)
        lastNode.next = null;
        lastNode.prev = null;
        // update size
        size--;
        return item;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node temp = sentinel.next;   // arr[0]
        for (int i=0; i<index; i++) {    // i=1, iterate 1 time, arr[1]
            temp = temp.next;
        }
        return temp.item;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }


    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node wizPos;

        public LinkedListDequeIterator() {
            wizPos = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return wizPos != sentinel;
        }

        @Override
        public T next() {
            T returnItem = wizPos.item;
            wizPos = wizPos.next;
            return  returnItem;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof LinkedListDeque61B otherLLD) {
            if (this.size != otherLLD.size) {
                return false;
            }
            for (int i=0; i<this.size; i++) {
                if (!this.get(i).equals(otherLLD.get(i))) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }


    public static void main(String[] args) {
        LinkedListDeque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(4);
        lld1.addLast(5);
        lld1.addLast(6);

//        Iterator<Integer> lld_seer = lld.iterator();
//        while(lld_seer.hasNext()) {
//            int i = lld_seer.next();
//            System.out.println(i);
//        }

        for (int i : lld1) {
            System.out.println(i);
        }

        LinkedListDeque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld2.addLast(4);
        lld2.addLast(5);
        lld2.addLast(6);

        System.out.println(lld1.equals(lld2));
    }
}
