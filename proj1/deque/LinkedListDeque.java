package deque;


import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    // nested class: IntNode, with 3 attributes: item (type T), prev (type IntNode), and next (type IntNode)
    private class IntNode {
        private T item;
        private IntNode prev;
        private IntNode next;

        public IntNode(T i, IntNode x, IntNode y) {
            item = i;
            prev = x;
            next = y;
        }

        /*
    public IntNode(T i) {
            item = i;
            prev = null;
            next = null;
        }
         */

    }


    // Two class attributes: sentinel and size
    private IntNode sentinel;
    private int size;


    private class LinkedListDequeIterator implements Iterator<T> {
        private int pos;
        public LinkedListDequeIterator() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T returnItem = get(pos);
            pos += 1;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    // Constructor
    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        sentinel.next = new IntNode(item, sentinel, sentinel.next);
        if (sentinel.prev.item == null) {
            sentinel.prev = sentinel.next;
        }
        if (sentinel.next.next.item != null) {
            sentinel.next.next.prev = sentinel.next;
        }

        /*
        New oldFront = sentinel.next;
        Node newNode = new Node(item);
        sentinel.next = newNode;
        newNode.prev = sentinel;
        oldFront.next = ..
        // Creating 4 pointers
         */

        size += 1;
    }

    @Override
    public void addLast(T item) {
        sentinel.prev = new IntNode(item, sentinel.prev, sentinel);
        if (sentinel.next.item == null) {
            sentinel.next = sentinel.prev;
        }
        if (sentinel.prev.prev.item != null) {
            sentinel.prev.prev.next = sentinel.prev;
        }
        size += 1;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public void printDeque() {
        IntNode p = sentinel.next;
        while (p.item != null) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println("");
    }

    @Override
    public T removeFirst() {
        if (sentinel.next.item == null) {
            return null;
        }
        T nextVal = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return nextVal;
    }


    @Override
    public T removeLast() {
        if (sentinel.prev.item == null) {
            return null;
        }
        T prevVal = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return prevVal;
    }



    @Override
    public T get(int index) {
        IntNode p = sentinel;
        while (index != 0) {
            p = p.next;
            index -= 1;
        }
        return p.next.item;
    }

    @Override
    public boolean equals(Object o) {
        if ((!(o instanceof Deque))) {
            return false;
        }
        Deque castedO = (Deque) o;
        if ((size() != (castedO.size()))) {
            return false;
        }
        if (this == o) {
            return true;
        }
        for (int i = 0; i < size(); i += 1) {
            if (!((Object) get(i)).equals((Object) (castedO.get(i)))) {
                return false;
            }
        }
        return true;
    }

    private T helper(int index, IntNode p) {
        if (index == 0) {   // if index > size || index < 0, return null
            return p.item;
        }
        return helper(index - 1, p.next);
    }

    public T getRecursive(int index) {

        return helper(index, sentinel.next);

    }

    /*


     */


}








