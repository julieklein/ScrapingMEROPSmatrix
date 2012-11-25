package selectingmeropsmatrix.test;

import java.util.ArrayList;
import java.util.List;

import selectingmeropsmatrix.DoubleBag;

public class DoubleBagTest {
    public static void main(String[] args) {
        List<Double> list = new ArrayList<Double>();
        DoubleBag bag = new DoubleBag();
        for (int i = 0; i < 100; i++) {
            double d = Math.random();
            list.add(d);
            bag.add(d);
        }
        System.out.println("DoubleBagTest.main() list " + list.size());
        System.out.println("DoubleBagTest.main() bag  " + list.size());
        for (int i = 0; i < 100; i++) {
            double e = list.get(i);
            list.add(e);
            bag.add(e);
        }
        System.out.println("DoubleBagTest.main() list " + list.size());
        System.out.println("DoubleBagTest.main() bag  " + list.size());
    }
}
