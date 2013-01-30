package selectingmeropsmatrix;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

public class DoubleBag {
    private static class Entry {
        Entry() {}
        double k;
        long occurrences = 0;
    }

    private static Comparator<Entry> comparator = new Comparator<Entry>() {
        @Override
        public int compare(Entry o1, Entry o2) {
            return Double.compare(o1.k, o2.k);
        }
    };
    private long size = 0;
    private int recordSize = 0;
    // private List<Entry> keys = new ArrayList<Entry>();
    private Map<Double, AtomicLong> keys = new TreeMap<Double, AtomicLong>();

    public DoubleBag() {}

    // public void add(double d) {
    // for (int i = 0; i < recordSize; i++) {
    // if (keys.get(i).k == d) {
    // keys.get(i).occurrences++;
    // size++;
    // return;
    // }
    // }
    // Entry e = new Entry();
    // e.k = d;
    // e.occurrences = 1;
    // recordSize++;
    // size++;
    // keys.add(e);
    // }

    public void add(Double d, long l) {
        AtomicLong counter = keys.get(d);
        if (counter == null) {
            counter = new AtomicLong();
            keys.put(d, counter);
        }
        counter.getAndAdd(l);
        // for (int i = 0; i < recordSize; i++) {
        // if (keys.get(i).k == d) {
        // keys.get(i).occurrences += l;
        // size += l;
        // return;
        // }
        // }
        // Entry e = new Entry();
        // e.k = d;
        // e.occurrences = 1;
        // recordSize++;
        size += l;
        // keys.add(e);
    }

    public long size() {
        return size;
    }

    /** @return list of unique values */
    public Double[] list() {
        Double[] toReturn = keys.keySet().toArray(new Double[recordSize]);
        // for (int i = 0; i < recordSize; i++) {
        // toReturn[i] = keys.get(i).k;
        // }
        return toReturn;
    }

    public void sort() {
        // Collections.sort(keys, comparator);
    }

    public double get(long position) {
        if (position < 0 || position >= size) {
            throw new NoSuchElementException("Position is past last element: " + position);
        }
        long index = 0;
        for (Map.Entry<Double, AtomicLong> e : keys.entrySet()) {
            index += e.getValue().get();
            if (position < index) {
                return e.getKey();
            }
        }
        throw new RuntimeException("could not find element position: " + position);
    }

    public long occurrences(Double d) {
        AtomicLong l = keys.get(d);
        if (l == null) {
            return 0;
        }
        return l.get();
    }
}
