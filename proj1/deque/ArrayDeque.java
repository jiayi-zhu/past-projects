package deque;
import java.util.Iterator;
public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int RESIZE_FACTOR = 2;
    private static final int DIVIDING_FACTOR = 4;
    private static final int MIN_LEN = 16;
    private static final int INITIAL_LEN = 8;
    private static final int INITIAL_START_POS = 4;
    private static final int INITIAL_END_POS = 5;


    private int nextIndex(int prevIndex, boolean movingLeft) {
        int nextInd;
        if (movingLeft) {
            nextInd = prevIndex - 1;
            if (nextInd < 0) {
                nextInd = items.length + nextInd;
            }
        } else {
            nextInd = (prevIndex + 1) % items.length;
        }
        return nextInd;
    }

    /*
    wrapIdex
    (index + items.length) % items.length

    Math.floorMod(index,items.length)
     */

    private int currFirstIndex() {
        int currFirst;
        currFirst = (nextFirst + 1) % items.length;
        return currFirst;
    }

    private int currLastIndex() {
        int currLast;
        currLast = nextLast - 1;
        if (currLast < 0) {
            currLast = items.length + currLast;
        }
        return currLast;
    }


    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        public ArrayDequeIterator() {
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
        return new ArrayDequeIterator();
    }

    public ArrayDeque() {
        items = (T[]) new Object[INITIAL_LEN];
        size = 0;
        nextFirst = INITIAL_START_POS;
        nextLast = INITIAL_END_POS;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }
        items[nextFirst] = x;
        size += 1;
        nextFirst = nextIndex(nextFirst, true);
        //nextFirst = wrapIndex(nextFirst - 1);
    }


    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }
        items[nextLast] = x;
        size += 1;
        nextLast = nextIndex(nextLast, false);
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        T returnItem = items[currFirstIndex()];
        items[currFirstIndex()] = null;
        size -= 1;
        nextFirst = currFirstIndex();
        if ((size < items.length / DIVIDING_FACTOR) && (size > 1) && (items.length >= MIN_LEN)) {
            resize(items.length / DIVIDING_FACTOR);
        }
        return returnItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T returnItem = items[currLastIndex()];
        items[currLastIndex()] = null;
        size -= 1;
        nextLast = currLastIndex();
        if ((size < items.length / DIVIDING_FACTOR) && (size > 1) && (items.length >= MIN_LEN)) {
            resize(items.length / DIVIDING_FACTOR);
        }
        return returnItem;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public T get(int i) {
        if (i < 0 || i >= size) {
            return null;
        }
        return items[(currFirstIndex() + i) % items.length];
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(get(i) + " ");
        }
        System.out.println("");
    }


    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 1; i <= size; i += 1) {
            a[i] = get(i - 1);
        }
        items = a;
        nextFirst = 0;
        nextLast = (size + 1) % items.length;
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
}


