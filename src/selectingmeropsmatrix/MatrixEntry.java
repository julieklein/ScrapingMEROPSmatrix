package selectingmeropsmatrix;

public class MatrixEntry {
    private String proteasesymbol;
    private String meropsurl;
    private String nP4 = "-";
    private String nP3 = "-";
    private String nP2 = "-";
    private String nP1 = "-";
    private String nP1prime = "-";
    private String nP2prime = "-";
    private String nP3prime = "-";
    private String nP4prime = "-";
    private int dP4 = 0;
    private int dP3 = 0;
    private int dP2 = 0;
    private int dP1 = 0;
    private int dP1prime = 0;
    private int dP2prime = 0;
    private int dP3prime = 0;
    private int dP4prime = 0;
    private double logDP4 = 0;
    private double logDP3 = 0;
    private double logDP2 = 0;
    private double logDP1 = 0;
    private double logDP1prime = 0;
    private double logDP2prime = 0;
    private double logDP3prime = 0;
    private double logDP4prime = 0;
    private double Q75;
    private double Q50;
    private double Q25;

    public double logSum() {
        return logDP4 + logDP3 + logDP2 + logDP1 + logDP1prime + logDP2prime
                + logDP3prime + logDP4prime;
    }

    public String getProteasesymbol() {
        return proteasesymbol;
    }

    public void setProteasesymbol(String proteasesymbol) {
        this.proteasesymbol = proteasesymbol;
    }

    public String getnP2() {
        return nP2;
    }

    public double getQ75() {
        return Q75;
    }

    public void setQ75(double q75) {
        Q75 = q75;
    }

    public double getQ50() {
        return Q50;
    }

    public void setQ50(double q50) {
        Q50 = q50;
    }

    public double getQ25() {
        return Q25;
    }

    public void setQ25(double q25) {
        Q25 = q25;
    }

    public String getMeropsurl() {
        return meropsurl;
    }

    public void setMeropsurl(String meropsurl) {
        this.meropsurl = meropsurl;
    }

    public String getnP4() {
        return nP4;
    }

    public String getnP3() {
        return nP3;
    }

    public String getnP1() {
        return nP1;
    }

    public String getnP1prime() {
        return nP1prime;
    }

    public String getnP2prime() {
        return nP2prime;
    }

    public String getnP3prime() {
        return nP3prime;
    }

    public String getnP4prime() {
        return nP4prime;
    }

    public int getdP4() {
        return dP4;
    }

    public void setValidity(boolean[] validity, int base) {
        double log = Math.log(base);
        if (validity[0]) {
            dP4 = base;
            logDP4 = log;
        } else {
            dP4 = 1;
            logDP4 = 0;
        }
        if (validity[1]) {
            dP3 = base;
            logDP3 = log;
        } else {
            dP3 = 1;
            logDP3 = 0;
        }
        if (validity[2]) {
            dP2 = base;
            logDP2 = log;
        } else {
            dP2 = 1;
            logDP2 = 0;
        }
        if (validity[3]) {
            dP1 = base;
            logDP1 = log;
        } else {
            dP1 = 1;
            logDP1 = 0;
        }
        if (validity[4]) {
            dP1prime = base;
            logDP1prime = log;
        } else {
            dP1prime = 1;
            logDP1prime = 0;
        }
        if (validity[5]) {
            dP2prime = base;
            logDP2prime = log;
        } else {
            dP2prime = 1;
            logDP2prime = 0;
        }
        if (validity[6]) {
            dP3prime = base;
            logDP3prime = log;
        } else {
            dP3prime = 1;
            logDP3prime = 0;
        }
        if (validity[7]) {
            dP4prime = base;
            logDP4prime = log;
        } else {
            dP4prime = 1;
            logDP4prime = 0;
        }
    }

    public void setdP4(int dP4) {
        this.dP4 = dP4;
        logDP4 = Math.log(dP4);
    }

    public int getdP3() {
        return dP3;
    }

    public void setdP3(int dP3) {
        this.dP3 = dP3;
        logDP3 = Math.log(dP3);
    }

    public int getdP2() {
        return dP2;
    }

    public void setdP2(int dP2) {
        this.dP2 = dP2;
        logDP2 = Math.log(dP2);
    }

    public int getdP1() {
        return dP1;
    }

    public void setdP1(int dP1) {
        this.dP1 = dP1;
        logDP1 = Math.log(dP1);
    }

    public int getdP1prime() {
        return dP1prime;
    }

    public void setdP1prime(int dP1prime) {
        this.dP1prime = dP1prime;
        logDP1prime = Math.log(dP1prime);
    }

    public int getdP2prime() {
        return dP2prime;
    }

    public void setdP2prime(int dP2prime) {
        this.dP2prime = dP2prime;
        logDP2prime = Math.log(dP2prime);
    }

    public int getdP3prime() {
        return dP3prime;
    }

    public void setdP3prime(int dP3prime) {
        this.dP3prime = dP3prime;
        logDP3prime = Math.log(dP3prime);
    }

    public int getdP4prime() {
        return dP4prime;
    }

    public void setdP4prime(int dPprime) {
        dP4prime = dPprime;
        logDP4prime = Math.log(dP4prime);
    }

    public static String join(int[][] numbers, int column) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            b.append(numbers[i][column]);
            if (i < 19) {
                b.append(", ");
            }
        }
        return b.toString();
    }

    public void set(int[][] Matrix) {
        nP4 = join(Matrix, 0);
        nP3 = join(Matrix, 1);
        nP2 = join(Matrix, 2);
        nP1 = join(Matrix, 3);
        nP1prime = join(Matrix, 4);
        nP2prime = join(Matrix, 5);
        nP3prime = join(Matrix, 6);
        nP4prime = join(Matrix, 7);
    }

    @Override
    public String toString() {
        return getProteasesymbol() + "\t" + nP4 + "\t" + nP3 + "\t" + nP2 + "\t" + nP1
                + "\t" + nP1prime + "\t" + nP2prime + "\t" + nP3prime + "\t" + nP4prime
                + "\t" + dP4 + "\t" + dP3 + "\t" + dP2 + "\t" + dP1 + "\t" + dP1prime
                + "\t" + dP2prime + "\t" + dP3prime + "\t" + dP4prime + "\t" + Q75 + "\t"
                + Q50 + "\t" + Q25 + "\t"
                + getMeropsurl();
    }
}
