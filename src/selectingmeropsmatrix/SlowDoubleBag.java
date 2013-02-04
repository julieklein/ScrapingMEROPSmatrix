package selectingmeropsmatrix;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

public class SlowDoubleBag {
    private double[] keys;
    private long[] occurrences;

    private long size = 0;
    private int recordSize = 0;

    public SlowDoubleBag(DoubleBag bag) {
        TreeMap<Double, AtomicLong> entries = bag.getEntries();
        recordSize = entries.size();
        keys = new double[recordSize];
        occurrences = new long[recordSize];
        int counter = 0;
        for (Map.Entry<Double, AtomicLong> e : entries.entrySet()) {
            keys[counter] = e.getKey();
            occurrences[counter] = e.getValue().get();

            size += occurrences[counter];
                counter++;
            }
    }



    /** @return list of unique values */
    public double[] keys() {
        return keys;
    }

    public long[] occurrences() {
        return occurrences;
    }

    public long getSize() {
        return size;
    }
}
