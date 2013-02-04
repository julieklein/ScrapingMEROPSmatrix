package selectingmeropsmatrix.test;

import selectingmeropsmatrix.MainSelecting2;
import selectingmeropsmatrix.MatrixEntry;

public class MeropTest {
    private static final double expected25 = -8.46803796061809;
    private static final double expected50 = -7.3694256719499815;
    private static final double expected75 = -6.165452867624046;

    public static void main(String[] args) throws Exception {
        new MeropTest().shouldGive678();
    }

    public void shouldGive678() throws Exception {
        MainSelecting2 selector = new MainSelecting2();
        String[] toUse = "XPNPEP2\t13\t0xxx0xxx0xxx3xxx0xxx0xxx3xxx0xxx0xxx0xxx0xxx0xxx13xxx10xxx0xxx1xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx2xxx0xxx0xxx0xxx1xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx1xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx1xxx0xxx0xxx0xxx0xxx3xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0xxx0\thttp://merops.sanger.ac.uk/cgi-bin/pepsum?id=M24.005"
                .split("\t");
        MatrixEntry e = selector.runOnFullEntry(toUse, "test");
        if (Math.abs(e.getQ75() - expected75) > 0.001) {
            System.out.println("MeropTest.shouldGive678() error on q75: " + e.getQ75());
        }
        if (Math.abs(e.getQ50() - expected50) > 0.001) {
            System.out.println("MeropTest.shouldGive678() error on q50: " + e.getQ50());
        }
        if (Math.abs(e.getQ25() - expected25) > 0.001) {
            System.out.println("MeropTest.shouldGive678() error on q25: " + e.getQ25());
        }
    }
}
