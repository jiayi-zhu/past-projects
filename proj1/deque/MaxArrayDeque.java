package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> defaultComparator;
    public MaxArrayDeque(Comparator<T> c) {
        defaultComparator = c;
    }


    public T max() {
        T maxItem = get(0);
        for (T item: this) {
            if (defaultComparator.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> c) {
        T maxItem = get(0);
        for (T item: this) {
            if (c.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }

}


