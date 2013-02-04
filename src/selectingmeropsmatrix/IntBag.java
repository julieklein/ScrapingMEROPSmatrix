package selectingmeropsmatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class IntBag {
    private static class Entry {
        Entry() {}

        int k;
        long occurrences = 0;
    }

    private static Comparator<Entry> comparator = new Comparator<Entry>() {
        @Override
        public int compare(Entry o1, Entry o2) {
            return Integer.compare(o1.k, o2.k);
        }
    };
    private long size = 0;
    private int recordSize = 0;
    private List<Entry> keys = new ArrayList<Entry>();


    public IntBag() {}

    public void add(int d) {
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

    public long size() {
        return size;
    }

    /** @return list of unique values */
    public int[] list() {
        int[] toReturn = new int[recordSize];
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
        long index = 0;
        for (int i = 0; i < recordSize; i++) {
            index += keys.get(i).occurrences;
            if (position < index) {
                return keys.get(i).k;
            }
        }
        throw new RuntimeException("could not find element position: " + position);
    }

    public long occurrences(int d) {
        for (int i = 0; i < recordSize; i++) {
            if (keys.get(i).k == d) {
                return keys.get(i).occurrences;
            }
        }
        return 0;
    }
}
