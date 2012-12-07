package selectingmeropsmatrix.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import selectingmeropsmatrix.DoubleBag;

public class DoubleBagTest {
    public static void main(String[] args) {
        List<Double> list = new ArrayList<Double>();
        DoubleBag bag = new DoubleBag();
        double d = Math.random();
        int _100 = 100000;
        for (int i = 0; i < _100; i++) {
            list.add(d + i);
            bag.add(d + i);
        }
        System.out.println("DoubleBagTest.main() list " + list.size());
        System.out.println("DoubleBagTest.main() bag  " + list.size());
        for (int i = 0; i < _100; i++) {
            double e = list.get(i);
            list.add(e);
            bag.add(e);
        }
        Collections.sort(list);
        bag.sort();
        System.out.println("DoubleBagTest.main() list " + list.size());
        System.out.println("DoubleBagTest.main() bag  " + list.size());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).doubleValue() != bag.get(i)) {
                System.out.println("DoubleBagTest.main() error: " + list.get(i) + "\t"
                        + bag.get(i));
            }
        }
    }
}
