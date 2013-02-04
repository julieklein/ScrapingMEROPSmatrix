package selectingmeropsmatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainSelecting2 {
    public List<String[]> getInputStrings(String fileName) throws IOException {
        List<String[]> toReturn = new ArrayList<String[]>();
        BufferedReader bReader = new BufferedReader(new FileReader(fileName));
        String line = bReader.readLine();
        while (line != null) {
            toReturn.add(line.split("\t"));
            line = bReader.readLine();
        }
        bReader.close();
        return toReturn;
    }

    // Faire le fichier
    public void save(String resultFileName, MatrixEntry... entries) {
        PrintStream csvWriter = null;
        try {
            csvWriter = new PrintStream(resultFileName);
            for (MatrixEntry entry : entries) {
                csvWriter.println(entry);
            }
            csvWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("MainSelecting2.save() failed to save " + resultFileName);
        }
    }

    public boolean runnable(String[] line) {
        return line.length > 3 && line[2].contains("xxx");
    }

    public MatrixEntry runOnEmptyInput(String[] line) {
        String meropsurl = line[3];
        String proteasename = line[0];
        MatrixEntry proteaseentry = new MatrixEntry();
        proteaseentry.setProteasesymbol(proteasename);
        proteaseentry.setMeropsurl(meropsurl);
        return proteaseentry;
    }

    // Lire les donnees pour chaque proteases
    public void check(String[] splitarray, String name) {
        String meropsurl = splitarray[3];
        String proteasename = splitarray[0];
        int matrixBase = Integer.parseInt(splitarray[1]);
        String splitmatrix[] = splitarray[2].split("xxx");
        int[][] Matrix = new int[20][8];
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 20; k++) {
                Matrix[k][i] = Integer.parseInt(splitmatrix[i + 8 * k]);
            }
        }
        if (isInteresting(Matrix)) {
            MatrixEntry proteaseentry = new MatrixEntry();
            proteaseentry.setProteasesymbol(proteasename);
            proteaseentry.setMeropsurl(meropsurl);
            proteaseentry.set(Matrix);
            boolean[] validity = isValid(Matrix);
            proteaseentry.setValidity(validity, matrixBase);
            int[] sortedColumns = sortColumns(Matrix);
            // TODO duplicate lines?
            // this entry is used to keep temporary values
            IntBag ip4Bag = buildBag(Matrix, validity, sortedColumns, 0);
            IntBag ip3Bag = buildBag(Matrix, validity, sortedColumns, 1);
            IntBag ip2Bag = buildBag(Matrix, validity, sortedColumns, 2);
            IntBag ip1Bag = buildBag(Matrix, validity, sortedColumns, 3);
            IntBag ip1pBag = buildBag(Matrix, validity, sortedColumns, 4);
            IntBag ip2pBag = buildBag(Matrix, validity, sortedColumns, 5);
            IntBag ip3pBag = buildBag(Matrix, validity, sortedColumns, 6);
            IntBag ip4pBag = buildBag(Matrix, validity, sortedColumns, 7);
            long total = ip1Bag.list().length;
            total *= ip2Bag.list().length;
            total *= ip3Bag.list().length;
            total *= ip4Bag.list().length;
            total *= ip1pBag.list().length;
            total *= ip2pBag.list().length;
            total *= ip3pBag.list().length;
            total *= ip4pBag.list().length;
            long all = total / 100;
            all = all == 0 ? 1 : all;
            System.out.println("MainSelecting2.runOnFullEntry() " + name + " "
                    + ip1Bag.list().length + " " + ip2Bag.list().length + " "
                    + ip3Bag.list().length + " " + ip4Bag.list().length + " "
                    + ip1pBag.list().length + " " + ip2pBag.list().length + " "
                    + ip3pBag.list().length + " " + ip4pBag.list().length + "\t" + total
                    + " (" + total / 2000000 + ")");
        }
    }


    // Lire les donnees pour chaque proteases
    public MatrixEntry runOnFullEntry(String[] splitarray, String name)
 throws Exception {
        String meropsurl = splitarray[3];
        String proteasename = splitarray[0];
        int matrixBase = Integer.parseInt(splitarray[1]);
        String splitmatrix[] = splitarray[2].split("xxx");
        // Choisir celles qui ont au moins une colonne avec un aa max
        // 10>=
        // aa min
        // Set[] sets = new Set[8];
        // sets[0] = new HashSet();
        // sets[1] = new HashSet();
        // sets[2] = new HashSet();
        // sets[3] = new HashSet();
        // sets[4] = new HashSet();
        // sets[5] = new HashSet();
        // sets[6] = new HashSet();
        // sets[7] = new HashSet();
        int[][] Matrix = new int[20][8];
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 20; k++) {
                Matrix[k][i] = Integer.parseInt(splitmatrix[i + 8 * k]);
                // sets[i].add(Matrix[k][i]);
            }
        }
        // System.out.println("MainSelecting2.runOnFullEntry() " +
        // sets[0].size() + " "
        // + sets[1].size() + " " + sets[2].size() + " " + sets[3].size() + " "
        // + sets[4].size() + " " + sets[5].size() + " " + sets[6].size() + " "
        // + sets[7].size());
        // check whether the matrix is interesting
        if (isInteresting(Matrix)) {
            MatrixEntry proteaseentry = new MatrixEntry();
            proteaseentry.setProteasesymbol(proteasename);
            proteaseentry.setMeropsurl(meropsurl);
            proteaseentry.set(Matrix);
            boolean[] validity = isValid(Matrix);
            proteaseentry.setValidity(validity, matrixBase);
            double proteaseLogSum = proteaseentry.logSum();
            // this is used to sort columns by the number of zeroes in them
            int[] sortedColumns = sortColumns(Matrix);
            // TODO duplicate lines?
            // this entry is used to keep temporary values
            MatrixEntry temp = new MatrixEntry();
            IntBag ip4Bag = buildBag(Matrix, validity, sortedColumns, 0);
            IntBag ip3Bag = buildBag(Matrix, validity, sortedColumns, 1);
            IntBag ip2Bag = buildBag(Matrix, validity, sortedColumns, 2);
            IntBag ip1Bag = buildBag(Matrix, validity, sortedColumns, 3);
            IntBag ip1pBag = buildBag(Matrix, validity, sortedColumns, 4);
            IntBag ip2pBag = buildBag(Matrix, validity, sortedColumns, 5);
            IntBag ip3pBag = buildBag(Matrix, validity, sortedColumns, 6);
            IntBag ip4pBag = buildBag(Matrix, validity, sortedColumns, 7);
            long total = ip1Bag.list().length;
            total *= ip2Bag.list().length;
            total *= ip3Bag.list().length;
            total *= ip4Bag.list().length;
            total *= ip1pBag.list().length;
            total *= ip2pBag.list().length;
            total *= ip3pBag.list().length;
            total *= ip4pBag.list().length;
            // System.out.println(ip1Bag.occurrences(1));
            // System.out.println(ip2Bag.occurrences(1));
            // System.out.println(ip3Bag.occurrences(1));
            // System.out.println(ip4Bag.occurrences(1));
            // System.out.println(ip1pBag.occurrences(1));
            // System.out.println(ip2pBag.occurrences(1));
            // System.out.println(ip3pBag.occurrences(1));
            // System.out.println(ip4pBag.occurrences(1));
            long all = total / 100;
            all = all == 0 ? 1 : all;
            long time = System.currentTimeMillis();
            System.out.println("MainSelecting2.runOnFullEntry() " + name + " "
                    + ip1Bag.list().length + " " + ip2Bag.list().length + " "
                    + ip3Bag.list().length + " " + ip4Bag.list().length + " "
                    + ip1pBag.list().length + " " + ip2pBag.list().length + " "
                    + ip3pBag.list().length + " " + ip4pBag.list().length + "\t" + total
                    + " (" + total / 2000000 + ")");
            // Compressor bags = new Compressor(name);
            DoubleBag bag = new DoubleBag();
            for (int P4 : ip4Bag.list()) {
                long repeatp4 = ip4Bag.occurrences(P4);
                temp.setdP4(P4);
                for (int P3 : ip3Bag.list()) {
                    long repeatp3 = ip3Bag.occurrences(P3);
                    temp.setdP3(P3);
                    for (int P2 : ip2Bag.list()) {

                        long repeatp2 = ip2Bag.occurrences(P2);
                        temp.setdP2(P2);
                        for (int P1 : ip1Bag.list()) {
                            long repeatp1 = ip1Bag.occurrences(P1);
                            temp.setdP1(P1);
                            for (int P1prime : ip1pBag.list()) {
                                long repeatp1p = ip1pBag.occurrences(P1prime);
                                temp.setdP1prime(P1prime);
                                for (int P2prime : ip2pBag.list()) {
                                    long repeatp2p = ip2pBag.occurrences(P2prime);
                                    temp.setdP2prime(P2prime);
                                    for (int P3prime : ip3pBag.list()) {
                                        long repeatp3p = ip3pBag.occurrences(P3prime);
                                        temp.setdP3prime(P3prime);
                                        for (int P4prime : ip4pBag.list()) {
                                            long repeatp4p = ip4pBag.occurrences(P4prime);
                                            temp.setdP4prime(P4prime);
                                            long repetitions = repeatp1 * repeatp1p
                                                    * repeatp2 * repeatp2p * repeatp3
                                                    * repeatp3p * repeatp4 * repeatp4p;
                                            bag.add(temp.logSum() - proteaseLogSum,
                                                    repetitions);
                                            total--;
                                            if (total % 10000000 == 0) {
                                                System.out
                                                        .println("MainSelecting2.runOnFullEntry() "
                                                                + name
                                                                + " still to do: "
                                                                + total
                                                                / all
                                                                + " ("
                                                                + (System
                                                                        .currentTimeMillis() - time)
                                                                + ")");
                                                System.out
                                                        .println("MainSelecting2.runOnFullEntry() record size "
                                                                + bag.getRecordSize());
                                                time = System.currentTimeMillis();
                                            }
                                            // if (bag.getRecordSize() >
                                            // 10000000) {
                                            // System.out
                                            // .println("MainSelecting2.runOnFullEntry() downloading to db");
                                            // for (Map.Entry<Double,
                                            // AtomicLong> e : bag
                                            // .getEntries().entrySet()) {
                                            // bags.put(e.getKey(), e.getValue()
                                            // .get());
                                            // }
                                            // bag.clear();
                                            // }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // for (Map.Entry<Double, AtomicLong> e :
            // bag.getEntries().entrySet()) {
            // bags.put(e.getKey(), e.getValue().get());
            // }
            // bag.clear();
            long quarter = bag.size() / 4;
            proteaseentry.setQ75(bag.get(quarter * 3));
            proteaseentry.setQ50(bag.get(quarter * 2));
            proteaseentry.setQ25(bag.get(quarter));
            return proteaseentry;
        }
        System.out.flush();
        return null;
    }

    // Lire les donnees pour chaque proteases
    public MatrixEntry _runOnFullEntry(String[] splitarray, String name) {
        String meropsurl = splitarray[3];
        String proteasename = splitarray[0];
        int matrixBase = Integer.parseInt(splitarray[1]);
        String splitmatrix[] = splitarray[2].split("xxx");
        // Choisir celles qui ont au moins une colonne avec un aa max
        // 10>=
        // aa min
        // Set[] sets = new Set[8];
        // sets[0] = new HashSet();
        // sets[1] = new HashSet();
        // sets[2] = new HashSet();
        // sets[3] = new HashSet();
        // sets[4] = new HashSet();
        // sets[5] = new HashSet();
        // sets[6] = new HashSet();
        // sets[7] = new HashSet();
        int[][] Matrix = new int[20][8];
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 20; k++) {
                Matrix[k][i] = Integer.parseInt(splitmatrix[i + 8 * k]);
                // sets[i].add(Matrix[k][i]);
            }
        }
        // System.out.println("MainSelecting2.runOnFullEntry() " +
        // sets[0].size() + " "
        // + sets[1].size() + " " + sets[2].size() + " " + sets[3].size() + " "
        // + sets[4].size() + " " + sets[5].size() + " " + sets[6].size() + " "
        // + sets[7].size());
        // check whether the matrix is interesting
        if (isInteresting(Matrix)) {
            MatrixEntry proteaseentry = new MatrixEntry();
            proteaseentry.setProteasesymbol(proteasename);
            proteaseentry.setMeropsurl(meropsurl);
            proteaseentry.set(Matrix);
            boolean[] validity = isValid(Matrix);
            proteaseentry.setValidity(validity, matrixBase);
            double proteaseLogSum = proteaseentry.logSum();
            // this is used to sort columns by the number of zeroes in them
            int[] sortedColumns = sortColumns(Matrix);
            // TODO duplicate lines?
            // this entry is used to keep temporary values
            MatrixEntry temp = new MatrixEntry();
            IntBag ip4Bag = buildBag(Matrix, validity, sortedColumns, 0);
            IntBag ip3Bag = buildBag(Matrix, validity, sortedColumns, 1);
            IntBag ip2Bag = buildBag(Matrix, validity, sortedColumns, 2);
            IntBag ip1Bag = buildBag(Matrix, validity, sortedColumns, 3);
            IntBag ip1pBag = buildBag(Matrix, validity, sortedColumns, 4);
            IntBag ip2pBag = buildBag(Matrix, validity, sortedColumns, 5);
            IntBag ip3pBag = buildBag(Matrix, validity, sortedColumns, 6);
            IntBag ip4pBag = buildBag(Matrix, validity, sortedColumns, 7);
            long total = ip1Bag.list().length;
            total *= ip2Bag.list().length;
            total *= ip3Bag.list().length;
            total *= ip4Bag.list().length;
            total *= ip1pBag.list().length;
            total *= ip2pBag.list().length;
            total *= ip3pBag.list().length;
            total *= ip4pBag.list().length;
            // System.out.println(ip1Bag.occurrences(1));
            // System.out.println(ip2Bag.occurrences(1));
            // System.out.println(ip3Bag.occurrences(1));
            // System.out.println(ip4Bag.occurrences(1));
            // System.out.println(ip1pBag.occurrences(1));
            // System.out.println(ip2pBag.occurrences(1));
            // System.out.println(ip3pBag.occurrences(1));
            // System.out.println(ip4pBag.occurrences(1));
            long all = total / 100;
            all = all == 0 ? 1 : all;
            long time = System.currentTimeMillis();
            System.out.println("MainSelecting2.runOnFullEntry() " + name + " "
                    + ip1Bag.list().length + " " + ip2Bag.list().length + " "
                    + ip3Bag.list().length + " " + ip4Bag.list().length + " "
                    + ip1pBag.list().length + " " + ip2pBag.list().length + " "
                    + ip3pBag.list().length + " " + ip4pBag.list().length + "\t" + total
                    + " (" + total / 2000000 + ")");
            MergeBag bags = new MergeBag();
            DoubleBag lnprobaarray = new DoubleBag();
            for (int P4 : ip4Bag.list()) {
                long repeatp4 = ip4Bag.occurrences(P4);
                temp.setdP4(P4);
                for (int P3 : ip3Bag.list()) {
                    long repeatp3 = ip3Bag.occurrences(P3);
                    temp.setdP3(P3);
                    for (int P2 : ip2Bag.list()) {
                        long repeatp2 = ip2Bag.occurrences(P2);
                        temp.setdP2(P2);
                        for (int P1 : ip1Bag.list()) {
                            long repeatp1 = ip1Bag.occurrences(P1);
                            temp.setdP1(P1);
                            for (int P1prime : ip1pBag.list()) {
                                long repeatp1p = ip1pBag.occurrences(P1prime);
                                temp.setdP1prime(P1prime);
                                for (int P2prime : ip2pBag.list()) {
                                    long repeatp2p = ip2pBag.occurrences(P2prime);
                                    temp.setdP2prime(P2prime);
                                    for (int P3prime : ip3pBag.list()) {
                                        long repeatp3p = ip3pBag.occurrences(P3prime);
                                        temp.setdP3prime(P3prime);
                                        for (int P4prime : ip4pBag.list()) {
                                            long repeatp4p = ip4pBag.occurrences(P4prime);
                                            temp.setdP4prime(P4prime);
                                            long repetitions = repeatp1 * repeatp1p
                                                    * repeatp2 * repeatp2p * repeatp3
                                                    * repeatp3p * repeatp4 * repeatp4p;
                                            lnprobaarray.add(temp.logSum()
                                                    - proteaseLogSum, repetitions);
                                            total--;
                                            if (total % 10000000 == 0) {
                                                System.out
                                                        .println("MainSelecting2.runOnFullEntry() "
                                                                + name
                                                                + " still to do: "
                                                                + total
                                                                / all
                                                                + " ("
                                                                + (System
                                                                        .currentTimeMillis() - time)
                                                                + ")");
                                                time = System.currentTimeMillis();
                                            }
                                            if (lnprobaarray.getRecordSize() > 1000000) {
                                                // System.out
                                                // .println("MainSelecting2.runOnFullEntry() unloading into collector");
                                                long start = System.currentTimeMillis();
                                                bags.add(new SlowDoubleBag(lnprobaarray));
                                                lnprobaarray = new DoubleBag();
                                                // System.out
                                                // .println("MainSelecting2.runOnFullEntry() unloaded: "
                                                // + (System
                                                // .currentTimeMillis() -
                                                // start));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            bags.add(new SlowDoubleBag(lnprobaarray));
            long quarter = bags.getSize() / 4;
            proteaseentry.setQ75(bags.find(quarter * 3));
            proteaseentry.setQ50(bags.find(quarter * 2));
            proteaseentry.setQ25(bags.find(quarter));
            return proteaseentry;
        }
        System.out.flush();
        return null;
    }

    private IntBag buildBag(int[][] Matrix, boolean[] validity, int[] sortedColumns,
            int index) {
        IntBag ip4Bag = new IntBag();
        for (int iP4 = 0; iP4 < 20; iP4++) {
            int P4 = 1;
            if (validity[sortedColumns[index]]) {
                P4 = Matrix[iP4][sortedColumns[index]];
            }
            if (P4 != 0) {
                ip4Bag.add(P4);
            }
        }
        return ip4Bag;
    }

    public int[] sortColumns(int[][] matrix) {
        int[] zeroes = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 20; i++) {
                if (matrix[i][j] == 0) {
                    zeroes[j] = zeroes[j] + 1;
                }
            }
        }
        int[] toReturn = new int[8];
        for (int i = 0; i < 8; i++) {
            int max = zeroes[0];
            int position = 0;
            for (int j = 1; j < 8; j++) {
                if (zeroes[j] > max) {
                    max = zeroes[j];
                    position = j;
                }
            }
            toReturn[i] = position;
            zeroes[position] = -1;
        }
        return toReturn;
    }

    public static void main(String[] args) throws Throwable {
        // TODO code application logic here
        final MainSelecting2 selector = new MainSelecting2();
        // selector.fileRun(selector.getInputStrings(args[0]),
        // "resultFile.csv");
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("MainSelecting2.main() running on " + availableProcessors
                + " processors");
        final ExecutorService runner = Executors.newFixedThreadPool(1);
        final ExecutorService baserunner = Executors
                .newFixedThreadPool(availableProcessors);
        List<String[]> inputStrings = selector.getInputStrings(args[0]);
        int counter = 0;
        final int total = inputStrings.size();
        for (String[] s : inputStrings) {
            counter++;
            if (s.length < 3) {
                continue;
            }
            final String[] values = s;
            final int current = counter;
            final String resultFileName = "resultFile_" + current + "_" + values[0]
                    + ".csv";
            if (selector.runnable(values)) {
                // System.out.println("MainSelecting2.main() submitted " +
                // current
                // + " of " + total + " : " + new Date());
                // _call(resultFileName, selector, values, current, total);
                final File f = new File("new/" + resultFileName);
                if (!f.exists()) {
                    _call(resultFileName, selector, values, current, total);
                    // Runnable task = new Runnable() {
                    // @Override
                    // public void run() {
                    // try {
                    // runner.submit(new Callable<String>() {
                    // @Override
                    // public String call() {
                    // return _call(resultFileName, selector, values,
                    // current, total);
                    // // final File f = new
                    // // File(resultFileName);
                    // // if (!f.exists()) {
                    // // try {
                    // // MatrixEntry e =
                    // // selector.runOnFullEntry(
                    // // values, resultFileName);
                    // // System.out
                    // // .println("MainSelecting2.main() finished "
                    // // + current
                    // // + " of "
                    // // + total
                    // // + " : " + new Date());
                    // // if (e != null) {
                    // // selector.save(resultFileName, e);
                    // // } else {
                    // // PrintStream p;
                    // // p = new PrintStream(f);
                    // // p.close();
                    // // }
                    // // } catch (Throwable e) {
                    // // System.out.println("MainSelecting2 error: "
                    // // + resultFileName);
                    // // e.printStackTrace();
                    // // }
                    // // }
                    // // return resultFileName;
                    // }
                    // }).get(200, TimeUnit.MINUTES);
                    // } catch (Exception e) {
                    // System.out.println("MainSelecting2 error: "
                    // + resultFileName);
                    // e.printStackTrace();
                    // }
                    // }
                    // };
                    // baserunner.submit(task);
                }
            } else {
                selector.save("new/" + resultFileName, selector.runOnEmptyInput(s));
            }
        }
        // runner.shutdown();
    }

    public static String _call(String resultFileName, MainSelecting2 selector,
            String[] values, int current, int total) {
        final File f = new File("new/" + resultFileName);
        if (!f.exists()) {
            try {
                MatrixEntry e = selector.runOnFullEntry(values, resultFileName);
                System.out.println("MainSelecting2.main() finished " + current + " of "
                        + total + " : " + new Date());
                if (e != null) {
                    selector.save("new/" + resultFileName, e);
                } else {
                    PrintStream p;
                    p = new PrintStream(f);
                    p.close();
                }
            } catch (Throwable e) {
                System.out.println("MainSelecting2 error: " + resultFileName);
                e.printStackTrace();
            }
        }
        return resultFileName;
    }

    public static void __call(String resultFileName, MainSelecting2 selector,
            String[] values, int current, int total) {
        final File f = new File("new/" + resultFileName);
        if (!f.exists()) {
            selector.check(values, resultFileName);
        }
    }

    private boolean isInteresting(int[][] Matrix) {
        return isInteresting(0, Matrix) || isInteresting(1, Matrix)
                || isInteresting(2, Matrix) || isInteresting(3, Matrix)
                || isInteresting(4, Matrix) || isInteresting(5, Matrix)
                || isInteresting(6, Matrix) || isInteresting(7, Matrix);
    }

    private boolean isInteresting(int i, int[][] Matrix) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int k = 0; k < 20; k++) {
            int current = Matrix[k][i];
            if (current < min) {
                min = current;
            }
            if (max < current) {
                max = current;
            }
        }
        if (min == 0 && max < 10) {
            return false;
        } else if (min >= 1 && max < 10 * min) {
            return false;
        }
        return true;
    }

    private boolean isValid(int j, int[][] Matrix) {
        for (int k = 0; k < 20; ++k) {
            if (Matrix[k][j] != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean[] isValid(int[][] Matrix) {
        boolean validityP4 = isValid(0, Matrix);
        boolean validityP3 = isValid(1, Matrix);
        boolean validityP2 = isValid(2, Matrix);
        boolean validityP1 = isValid(3, Matrix);
        boolean validityP1prime = isValid(4, Matrix);
        boolean validityP2prime = isValid(5, Matrix);
        boolean validityP3prime = isValid(6, Matrix);
        boolean validityP4prime = isValid(7, Matrix);
        return new boolean[] { validityP4, validityP3, validityP2, validityP1,
                validityP1prime, validityP2prime, validityP3prime, validityP4prime };
    }
}
