package selectingmeropsmatrix.test;

import java.util.Arrays;

import selectingmeropsmatrix.MainSelecting2;

public class SortTest {
    public static void main(String[] args) {
        int[][] test = new int[20][8];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 8; j++) {
                test[i][j] = j + 5;
            }
            for (int j = 0; j < 8; j++) {
                if (j >= i) {
                    test[i][j] = 0;
                }
            }
        }
        test[10][5] = 0;
        System.out.println("SortTest.main()" + Arrays.toString(test[0]));
        System.out.println("SortTest.main()" + Arrays.toString(test[1]));
        System.out.println("SortTest.main()" + Arrays.toString(test[2]));
        System.out.println("SortTest.main()" + Arrays.toString(test[3]));
        System.out.println("SortTest.main()" + Arrays.toString(test[4]));
        System.out.println("SortTest.main()" + Arrays.toString(test[5]));
        System.out.println("SortTest.main()" + Arrays.toString(test[6]));
        System.out.println("SortTest.main()" + Arrays.toString(test[7]));
        System.out.println("SortTest.main()" + Arrays.toString(test[8]));
        System.out.println("SortTest.main()" + Arrays.toString(test[9]));
        System.out.println("SortTest.main()" + Arrays.toString(test[10]));
        System.out.println("SortTest.main()" + Arrays.toString(test[11]));
        System.out.println("SortTest.main()" + Arrays.toString(test[12]));
        System.out.println("SortTest.main()" + Arrays.toString(test[13]));
        System.out.println("SortTest.main()" + Arrays.toString(test[14]));
        System.out.println("SortTest.main()" + Arrays.toString(test[15]));
        System.out.println("SortTest.main()" + Arrays.toString(test[16]));
        System.out.println("SortTest.main()" + Arrays.toString(test[17]));
        System.out.println("SortTest.main()" + Arrays.toString(test[18]));
        System.out.println("SortTest.main()" + Arrays.toString(test[19]));
        MainSelecting2 m = new MainSelecting2();
        int[] sorted = m.sortColumns(test);
        System.out.println("SortTest.main() sorted " + Arrays.toString(sorted));
    }
}
