package selectingmeropsmatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class DoubleBag {
    private static class Entry {
        Entry() {}
        double k;
        int occurrences;
    }

    private static Comparator<Entry> comparator = new Comparator<Entry>() {
        @Override
        public int compare(Entry o1, Entry o2) {
            return Double.compare(o1.k, o2.k);
        }
    };
    private int size = 0;
    private int recordSize = 0;
    private List<Entry> keys = new ArrayList<Entry>();


    public DoubleBag() {}

    public void add(double d) {
        for (int i = 0; i < recordSize; i++) {
            if (keys.get(i).k == d) {
                keys.get(i).occurrences++;
                size++;
                return;
            }
        }
        Entry e = new Entry();
        e.k = d;
        e.occurrences = 1;
        recordSize++;
        size++;
        keys.add(e);
    }

    public int size() {
        return size;
    }

    /** @return list of unique values */
    public double[] list() {
        double[] toReturn = new double[recordSize];
        for (int i = 0; i < recordSize; i++) {
            toReturn[i] = keys.get(i).k;
        }
        return toReturn;
    }

    public void sort() {
        Collections.sort(keys, comparator);
    }

    public double get(long position) {
        if (position < 0 || position >= size) {
            throw new NoSuchElementException("Position is past last element: " + position);
        }
        int index = 0;
        for (int i = 0; i < recordSize; i++) {
            index += keys.get(i).occurrences;
            if (position < index) {
                return keys.get(i).k;
            }
        }
        throw new RuntimeException("could not find element position: " + position);
    }

    public int occurrences(double d) {
        for (int i = 0; i < recordSize; i++) {
            if (keys.get(i).k == d) {
                return keys.get(i).occurrences;
            }
        }
        return 0;
    }
}
