package android.s305089.gameoflife.board;

import java.io.Serializable;

/**
 * Created by s305089 on 02.03.2016.
 */
public class ConwaysRule {
    private boolean toLive(byte cellToCheck) {
        return cellToCheck == 66 || cellToCheck == 67;
    }

    private boolean toSpawn(byte cellToCheck) {
        return cellToCheck == 3;
    }

    public byte setLife(byte cellToCheck) {
        if (toLive(cellToCheck) || toSpawn(cellToCheck)) {
            return 64;
        }
        return 0;
    }
}
