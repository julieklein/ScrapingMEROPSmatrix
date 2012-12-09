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
        return line[2].contains("xxx");
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
    public MatrixEntry runOnFullEntry(String[] splitarray) {
        String meropsurl = splitarray[3];
        String proteasename = splitarray[0];
        int matrixBase = Integer.parseInt(splitarray[1]);
        String splitmatrix[] = splitarray[2].split("xxx");
        // Choisir celles qui ont au moins une colonne avec un aa max
        // 10>=
        // aa min
        int[][] Matrix = new int[20][8];
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 20; k++) {
                Matrix[k][i] = Integer.parseInt(splitmatrix[i + 8 * k]);
            }
        }
        // check whether the matrix is interesting
        if (isInteresting(Matrix)) {
            MatrixEntry proteaseentry = new MatrixEntry();
            proteaseentry.setProteasesymbol(proteasename);
            proteaseentry.setMeropsurl(meropsurl);
            proteaseentry.set(Matrix);
            boolean[] validity = isValid(Matrix);
            proteaseentry.setValidity(validity, matrixBase);
            double proteaseLogSum = proteaseentry.logSum();
            DoubleBag lnprobaarray = new DoubleBag();
            // this is used to sort columns by the number of zeroes in them
            int[] sortedColumns = sortColumns(Matrix);
            // TODO duplicate lines?
            // this entry is used to keep temporary values
            MatrixEntry temp = new MatrixEntry();
            for (int iP4 = 0; iP4 < 20; iP4++) {
                int P4 = 1;
                if (validity[sortedColumns[0]]) {
                    P4 = Matrix[iP4][sortedColumns[0]];
                }
                if (P4 != 0) {
                    temp.setdP4(P4);
                    for (int iP3 = 0; iP3 < 20; iP3++) {
                        int P3 = 1;
                        if (validity[sortedColumns[1]]) {
                            P3 = Matrix[iP3][sortedColumns[1]];
                        }
                        if (P3 != 0) {
                            temp.setdP3(P3);
                            for (int iP2 = 0; iP2 < 20; iP2++) {
                                int P2 = 1;
                                if (validity[sortedColumns[2]]) {
                                    P2 = Matrix[iP2][sortedColumns[2]];
                                }
                                if (P2 != 0) {
                                    temp.setdP2(P2);
                                    for (int iP1 = 0; iP1 < 20; iP1++) {
                                        int P1 = 1;
                                        if (validity[sortedColumns[3]]) {
                                            P1 = Matrix[iP1][sortedColumns[3]];
                                        }
                                        if (P1 != 0) {
                                            temp.setdP1(P1);
                                            for (int iP1p = 0; iP1p < 20; iP1p++) {
                                                int P1prime = 1;
                                                if (validity[sortedColumns[4]]) {
                                                    P1prime = Matrix[iP1p][sortedColumns[4]];
                                                }
                                                if (P1prime != 0) {
                                                    temp.setdP1prime(P1prime);
                                                    for (int iP2p = 0; iP2p < 20; iP2p++) {
                                                        int P2prime = 1;
                                                        if (validity[sortedColumns[5]]) {
                                                            P2prime = Matrix[iP2p][sortedColumns[5]];
                                                        }
                                                        if (P2prime != 0) {
                                                            temp.setdP2prime(P2prime);
                                                            for (int iP3p = 0; iP3p < 20; iP3p++) {
                                                                int P3prime = 1;
                                                                if (validity[sortedColumns[6]]) {
                                                                    P3prime = Matrix[iP3p][sortedColumns[6]];
                                                                }
                                                                if (P3prime != 0) {
                                                                    temp.setdP3prime(P3prime);
                                                                    for (int iP4p = 0; iP4p < 20; iP4p++) {
                                                                        int P4prime = 1;
                                                                        if (validity[sortedColumns[7]]) {
                                                                            P4prime = Matrix[iP4p][sortedColumns[7]];
                                                                        }
                                                                        if (P4prime != 0) {
                                                                            temp.setdP4prime(P4prime);
                                                                            lnprobaarray
                                                                                    .add(temp
                                                                                            .logSum()
                                                                                            - proteaseLogSum);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            lnprobaarray.sort();
            long quarter = lnprobaarray.size() / 4;
            proteaseentry.setQ75(lnprobaarray.get(quarter * 3));
            proteaseentry.setQ50(lnprobaarray.get(quarter * 2));
            proteaseentry.setQ25(lnprobaarray.get(quarter));
            return proteaseentry;
        }
        return null;
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
        ExecutorService runner = Executors.newFixedThreadPool(availableProcessors);
        List<String[]> inputStrings = selector.getInputStrings(args[0]);
        int counter = 0;
        final int total = inputStrings.size();
        for (String[] s : inputStrings) {
            final String[] values = s;
            counter++;
            final int current = counter;
            if (selector.runnable(values)) {
                runner.submit(new Runnable() {
                    @Override
                    public void run() {
                        String resultFileName = "resultFile_" + current + "_" + values[0]
                                + ".csv";
                        File f = new File(resultFileName);
                        if (!f.exists()) {
                            System.out.println("MainSelecting2.main() submitted "
                                    + current + " of " + total + " : " + new Date());
                            MatrixEntry e = selector.runOnFullEntry(values);
                            System.out.println("MainSelecting2.main() finished "
                                    + current + " of " + total + " : " + new Date());
                            if (e != null) {
                                selector.save(resultFileName, e);
                            } else {
                                PrintStream p;
                                try {
                                    p = new PrintStream(f);
                                    p.close();
                                } catch (FileNotFoundException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }
            // else {
            // selector.save("resultFile_empty_" + s[0] + ".csv",
            // selector.runOnEmptyInput(s));
            // }
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
