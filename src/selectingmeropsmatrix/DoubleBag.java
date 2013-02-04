package selectingmeropsmatrix;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

public class DoubleBag {

    private long size = 0;
    private int recordSize = 0;
    private TreeMap<Double, AtomicLong> keys = new TreeMap<Double, AtomicLong>();

    public DoubleBag() {}

    private static double sigDigits = 100000;
    public void add(Double d, long l) {
        d = Math.round(d * sigDigits) / sigDigits;
        AtomicLong counter = keys.get(d);
        if (counter == null) {
            counter = new AtomicLong();
            keys.put(d, counter);
            recordSize++;
        }
        counter.getAndAdd(l);
        size += l;
    }

    public TreeMap<Double, AtomicLong> getEntries() {
        return keys;
    }
    public long size() {
        return size;
    }

    public int getRecordSize() {
        return recordSize;
    }

    /** @return list of unique values */
    public Double[] list() {
        Double[] toReturn = keys.keySet().toArray(new Double[recordSize]);
        return toReturn;
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

    public void clear() {
        keys.clear();
        recordSize = 0;
        size = 0;
    }
}
