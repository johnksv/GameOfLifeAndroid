package android.s305089.gameoflife.board;

/**
 * Created by s305089 on 15.03.2016.
 */
public final class BoardUsefullMethods {

    /**
     * Sets all the ones in the byte to 64.
     * NB: This method manipulates the array directly
     *
     * @param boardToConvert
     * @return
     */
    public static void setOnesTo64(byte[][] boardToConvert) {
        for (int i = 0; i < boardToConvert.length; i++) {
            for (int j = 0; j < boardToConvert.length; j++) {
                if (boardToConvert[i][j] == 1) {
                    boardToConvert[i][j] = 64;
                }
            }
        }
    }
}
