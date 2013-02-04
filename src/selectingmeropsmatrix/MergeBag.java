package selectingmeropsmatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class MergeBag {
    List<SlowDoubleBag> bags = new ArrayList<SlowDoubleBag>();
    long size = 0;

    public void add(SlowDoubleBag d) {
        bags.add(d);
        size += d.getSize();
    }

    public double find(long position) {
        if (position < 0 || position >= size) {
            throw new NoSuchElementException("Position is past last element: " + position);
        }
        int[] indexes = new int[bags.size()];
        Arrays.fill(indexes, 0);
        long crawled = 0;
        while (crawled <= size) {
            double min = Double.MAX_VALUE;
            long occurrences = 0;
            int toAdvance = 0;
            for (int i = 0; i < indexes.length; i++) {
                if (bags.get(i).keys()[indexes[i]] < min) {
                    min = bags.get(i).keys()[indexes[i]];
                    occurrences = bags.get(i).occurrences()[indexes[i]];
                    toAdvance = i;
                }
            }
            indexes[toAdvance] = indexes[toAdvance] + 1;
            crawled += occurrences;
            if (position < crawled) {
                return min;
            }
        }
        throw new RuntimeException("could not find element position: " + position);
    }

    public long getSize() {
        return size;
    }
}
