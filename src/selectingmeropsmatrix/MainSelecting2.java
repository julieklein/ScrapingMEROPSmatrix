package selectingmeropsmatrix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainSelecting2 {
    private int[][] Matrix = new int[20][8];
    private int[] interesting = new int[20];

    public MainSelecting2(String filename) throws IOException {
        // Lire les donnees pour chaque proteases
        BufferedReader bReader = createBufferedreader(filename);
        String line;
        LinkedList<MatrixEntry> allmatrixout = new LinkedList<MatrixEntry>();
        while ((line = bReader.readLine()) != null) {
            String splitarray[] = line.split("\t");
            String proteasename = splitarray[0];
            String matrixbasedon = splitarray[1];
            String meropsurl = splitarray[3];
            if (splitarray[2].contains("xxx")) {
                String splitmatrix[] = splitarray[2].split("xxx");
                System.out.println(splitarray[2]);
                // Choisir celles qui ont au moins une colonne avec un aa max
                // 10>=
                // aa min
                for (int i = 0; i < 8; i++) {
                    for (int k = 0; k < 20; k++) {
                        Matrix[k][i] = Integer.parseInt(splitmatrix[i + 8 * k]);
                    }
                }
                boolean interestingP4 = isInteresting(0);
                boolean interestingP3 = isInteresting(1);
                boolean interestingP2 = isInteresting(2);
                boolean interestingP1 = isInteresting(3);
                boolean interestingP1prime = isInteresting(4);
                boolean interestingP2prime = isInteresting(5);
                boolean interestingP3prime = isInteresting(6);
                boolean interestingP4prime = isInteresting(7);
                // check whether the matrix is interesting
                if (interestingP4 || interestingP3 || interestingP2 || interestingP1
                        || interestingP1prime || interestingP2prime || interestingP3prime
                        || interestingP4prime) {
                    MatrixEntry proteaseentry = new MatrixEntry();
                    proteaseentry.setProteasesymbol(proteasename);
                    proteaseentry.setMeropsurl(meropsurl);
                    int[] nP4 = new int[20];
                    int[] nP3 = new int[20];
                    int[] nP2 = new int[20];
                    int[] nP1 = new int[20];
                    int[] nP1prime = new int[20];
                    int[] nP2prime = new int[20];
                    int[] nP3prime = new int[20];
                    int[] nP4prime = new int[20];
                    for (int k = 0; k < 20; k++) {
                        nP4[k] = Matrix[k][0];
                    }
                    proteaseentry.nP4 = Arrays.toString(nP4);
                    proteaseentry.nP4 = proteaseentry.nP4.replaceAll("\\[", "");
                    proteaseentry.nP4 = proteaseentry.nP4.replaceAll("\\]", "");
                    for (int k = 0; k < 20; k++) {
                        nP3[k] = Matrix[k][1];
                    }
                    proteaseentry.nP3 = Arrays.toString(nP3);
                    proteaseentry.nP3 = proteaseentry.nP3.replaceAll("\\[", "");
                    proteaseentry.nP3 = proteaseentry.nP3.replaceAll("\\]", "");
                    for (int k = 0; k < 20; k++) {
                        nP2[k] = Matrix[k][2];
                    }
                    proteaseentry.nP2 = Arrays.toString(nP2);
                    proteaseentry.nP2 = proteaseentry.nP2.replaceAll("\\[", "");
                    proteaseentry.nP2 = proteaseentry.nP2.replaceAll("\\]", "");
                    for (int k = 0; k < 20; k++) {
                        nP1[k] = Matrix[k][3];
                    }
                    proteaseentry.nP1 = Arrays.toString(nP1);
                    proteaseentry.nP1 = proteaseentry.nP1.replaceAll("\\[", "");
                    proteaseentry.nP1 = proteaseentry.nP1.replaceAll("\\]", "");
                    for (int k = 0; k < 20; k++) {
                        nP1prime[k] = Matrix[k][4];
                    }
                    proteaseentry.nP1prime = Arrays.toString(nP1prime);
                    proteaseentry.nP1prime = proteaseentry.nP1prime.replaceAll("\\[", "");
                    proteaseentry.nP1prime = proteaseentry.nP1prime.replaceAll("\\]", "");
                    for (int k = 0; k < 20; k++) {
                        nP2prime[k] = Matrix[k][5];
                    }
                    proteaseentry.nP2prime = Arrays.toString(nP2prime);
                    proteaseentry.nP2prime = proteaseentry.nP2prime.replaceAll("\\[", "");
                    proteaseentry.nP2prime = proteaseentry.nP2prime.replaceAll("\\]", "");
                    for (int k = 0; k < 20; k++) {
                        nP3prime[k] = Matrix[k][6];
                    }
                    proteaseentry.nP3prime = Arrays.toString(nP3prime);
                    proteaseentry.nP3prime = proteaseentry.nP3prime.replaceAll("\\[", "");
                    proteaseentry.nP3prime = proteaseentry.nP3prime.replaceAll("\\]", "");
                    for (int k = 0; k < 20; k++) {
                        nP4prime[k] = Matrix[k][7];
                    }
                    proteaseentry.nP4prime = Arrays.toString(nP4prime);
                    proteaseentry.nP4prime = proteaseentry.nP4prime.replaceAll("\\[", "");
                    proteaseentry.nP4prime = proteaseentry.nP4prime.replaceAll("\\]", "");
                    boolean validityP4 = isValid(0);
                    boolean validityP3 = isValid(1);
                    boolean validityP2 = isValid(2);
                    boolean validityP1 = isValid(3);
                    boolean validityP1prime = isValid(4);
                    boolean validityP2prime = isValid(5);
                    boolean validityP3prime = isValid(6);
                    boolean validityP4prime = isValid(7);
                    int P4 = 1;
                    int P3 = 1;
                    int P2 = 1;
                    int P1 = 1;
                    int P1prime = 1;
                    int P2prime = 1;
                    int P3prime = 1;
                    int P4prime = 1;
                    int dP4 = validityP4 ? Integer.parseInt(matrixbasedon) : 1;
                    int dP3 = validityP3 ? Integer.parseInt(matrixbasedon) : 1;
                    int dP2 = validityP2 ? Integer.parseInt(matrixbasedon) : 1;
                    int dP1 = validityP1 ? Integer.parseInt(matrixbasedon) : 1;
                    int dP1prime = validityP1prime ? Integer.parseInt(matrixbasedon) : 1;
                    int dP2prime = validityP2prime ? Integer.parseInt(matrixbasedon) : 1;
                    int dP3prime = validityP3prime ? Integer.parseInt(matrixbasedon) : 1;
                    int dP4prime = validityP4prime ? Integer.parseInt(matrixbasedon) : 1;
                    proteaseentry.dP4 = dP4;
                    proteaseentry.dP3 = dP3;
                    proteaseentry.dP2 = dP2;
                    proteaseentry.dP1 = dP1;
                    proteaseentry.dP1prime = dP1prime;
                    proteaseentry.dP2prime = dP2prime;
                    proteaseentry.dP3prime = dP3prime;
                    proteaseentry.dP4prime = dP4prime;
                    double lndP4 = Math.log(dP4);
                    double lndP3 = Math.log(dP3);
                    double lndP2 = Math.log(dP2);
                    double lndP1 = Math.log(dP1);
                    double lndP1prime = Math.log(dP1prime);
                    double lndP2prime = Math.log(dP2prime);
                    double lndP3prime = Math.log(dP3prime);
                    double lndP4prime = Math.log(dP4prime);
                    DoubleBag lnprobaarray = new DoubleBag();
                    for (int iP4 = 0; iP4 < 20; iP4++) {
                        if (validityP4) {
                            P4 = Matrix[iP4][0];
                        }
                        if (P4 != 0) {
                            for (int iP3 = 0; iP3 < 20; iP3++) {
                                if (validityP3) {
                                    P3 = Matrix[iP3][1];
                                }
                                if (!(P3 == 0)) {
                                    for (int iP2 = 0; iP2 < 20; iP2++) {
                                        if (validityP2) {
                                            P2 = Matrix[iP2][2];
                                        }
                                        if (!(P2 == 0)) {
                                            for (int iP1 = 0; iP1 < 20; iP1++) {
                                                if (validityP1) {
                                                    P1 = Matrix[iP1][3];
                                                }
                                                if (!(P1 == 0)) {
                                                    for (int iP1p = 0; iP1p < 20; iP1p++) {
                                                        if (validityP1prime) {
                                                            P1prime = Matrix[iP1p][4];
                                                        }
                                                        if (!(P1prime == 0)) {
                                                            for (int iP2p = 0; iP2p < 20; iP2p++) {
                                                                if (validityP2prime) {
                                                                    P2prime = Matrix[iP2p][5];
                                                                }
                                                                if (!(P2prime == 0)) {
                                                                    for (int iP3p = 0; iP3p < 20; iP3p++) {
                                                                        if (validityP3prime) {
                                                                            P3prime = Matrix[iP3p][6];
                                                                        }
                                                                        if (!(P3prime == 0)) {
                                                                            for (int iP4p = 0; iP4p < 20; iP4p++) {
                                                                                if (validityP4prime) {
                                                                                    P4prime = Matrix[iP4p][7];
                                                                                }
                                                                                if (!(P4prime == 0)) {
                                                                                    double proba = compute(
                                                                                            P4,
                                                                                            P3,
                                                                                            P2,
                                                                                            P1,
                                                                                            P1prime,
                                                                                            P2prime,
                                                                                            P3prime,
                                                                                            P4prime,
                                                                                            lndP4,
                                                                                            lndP3,
                                                                                            lndP2,
                                                                                            lndP1,
                                                                                            lndP1prime,
                                                                                            lndP2prime,
                                                                                            lndP3prime,
                                                                                            lndP4prime);
                                                                                    lnprobaarray
                                                                                            .add(proba);
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
                    System.out.println(lnprobaarray.size());
                    // Collections.sort(lnprobaarray);
                    lnprobaarray.sort();
                    // double upper = 0.75;
                    // double middle = 0.50;
                    // double low = 0.25;
                    long quarter = lnprobaarray.size() / 4;
                    long posQ75 = quarter * 3;
                    long posQ50 = quarter * 2;
                    long posQ25 = quarter;
                    double Q75 = lnprobaarray.get(posQ75);
                    double Q50 = lnprobaarray.get(posQ50);
                    double Q25 = lnprobaarray.get(posQ25);
                    System.out.println(Q75);
                    System.out.println(Q50);
                    System.out.println(Q25);
                    proteaseentry.setQ75(Q75);
                    proteaseentry.setQ50(Q50);
                    proteaseentry.setQ25(Q25);
                    allmatrixout.add(proteaseentry);
                }
            } else {
                MatrixEntry proteaseentry = new MatrixEntry();
                proteaseentry.setProteasesymbol(proteasename);
                proteaseentry.setMeropsurl(meropsurl);
                proteaseentry.nP4 = "-";
                proteaseentry.nP3 = "-";
                proteaseentry.nP2 = "-";
                proteaseentry.nP1 = "-";
                proteaseentry.nP1prime = "-";
                proteaseentry.nP2prime = "-";
                proteaseentry.nP3prime = "-";
                proteaseentry.nP4prime = "-";
                proteaseentry.dP4 = 0;
                proteaseentry.dP3 = 0;
                proteaseentry.dP2 = 0;
                proteaseentry.dP1 = 0;
                proteaseentry.dP1prime = 0;
                proteaseentry.dP2prime = 0;
                proteaseentry.dP3prime = 0;
                proteaseentry.dP4prime = 0;
                allmatrixout.add(proteaseentry);
            }
        }
        // Faire le fichier
        PrintStream csvWriter = null;
        try {
            System.out.println("-----------------");
            csvWriter = new PrintStream("testresult.txt");
            for (MatrixEntry entry : allmatrixout) {
                populateData(csvWriter, entry);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainSelecting.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            csvWriter.close();
        }
    }

    private double compute(int P4, int P3, int P2, int P1, int P1prime, int P2prime,
            int P3prime, int P4prime, double lndP4, double lndP3, double lndP2,
            double lndP1, double lndP1prime, double lndP2prime, double lndP3prime,
            double lndP4prime) {
        double lnP4 = Math.log(P4);
        double lnP3 = Math.log(P3);
        double lnP2 = Math.log(P2);
        double lnP1 = Math.log(P1);
        double lnP1prime = Math.log(P1prime);
        double lnP2prime = Math.log(P2prime);
        double lnP3prime = Math.log(P3prime);
        double lnP4prime = Math.log(P4prime);
        double proba = lnP4
                + lnP3
                + lnP2
                + lnP1
                + lnP1prime
                + lnP2prime
                + lnP3prime
                + lnP4prime
                - (lndP4 + lndP3 + lndP2 + lndP1 + lndP1prime + lndP2prime + lndP3prime + lndP4prime);
        return proba;
    }

    public static void main(String[] args) throws Throwable {
        // TODO code application logic here
        MainSelecting2 MainSelecting = new MainSelecting2(args[0]);
    }

    private BufferedReader createBufferedreader(String datafilename)
            throws FileNotFoundException {
        BufferedReader bReader = new BufferedReader(new FileReader(datafilename));
        return bReader;
    }

    private void populateData(PrintStream csvWriter, MatrixEntry entry) {
        csvWriter.print(entry.getProteasesymbol());
        csvWriter.print("\t");
        csvWriter.print(entry.getnP4());
        csvWriter.print("\t");
        csvWriter.print(entry.getnP3());
        csvWriter.print("\t");
        csvWriter.print(entry.getnP2());
        csvWriter.print("\t");
        csvWriter.print(entry.getnP1());
        csvWriter.print("\t");
        csvWriter.print(entry.getnP1prime());
        csvWriter.print("\t");
        csvWriter.print(entry.getnP2prime());
        csvWriter.print("\t");
        csvWriter.print(entry.getnP3prime());
        csvWriter.print("\t");
        csvWriter.print(entry.getnP4prime());
        csvWriter.print("\t");
        csvWriter.print(entry.getdP4());
        csvWriter.print("\t");
        csvWriter.print(entry.getdP3());
        csvWriter.print("\t");
        csvWriter.print(entry.getdP2());
        csvWriter.print("\t");
        csvWriter.print(entry.getdP1());
        csvWriter.print("\t");
        csvWriter.print(entry.getdP1prime());
        csvWriter.print("\t");
        csvWriter.print(entry.getdP2prime());
        csvWriter.print("\t");
        csvWriter.print(entry.getdP3prime());
        csvWriter.print("\t");
        csvWriter.print(entry.getdP4prime());
        csvWriter.print("\t");
        csvWriter.print(entry.getQ75());
        csvWriter.print("\t");
        csvWriter.print(entry.getQ50());
        csvWriter.print("\t");
        csvWriter.print(entry.getQ25());
        csvWriter.print("\t");
        csvWriter.print(entry.getMeropsurl());
        csvWriter.print("\n");
    }

    private boolean isInteresting(int i) {
        for (int k = 0; k < 20; k++) {
            interesting[k] = Matrix[k][i];
        }
        Arrays.sort(interesting);
        int min = interesting[0];
        int max = interesting[19];
        if (min == 0 && max < 10) {
            return false;
        } else if (min >= 1 && max < 10 * min) {
            return false;
        }
        return true;
    }

    private boolean isValid(int j) {
        for (int k = 0; k < 20; ++k) {
            if (Matrix[k][j] != 0) {
                return true;
            }
        }
        return false;
    }
}
