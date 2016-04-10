package android.s305089.gameoflife.board;

import java.io.Serializable;

/**
 * Created by s305089 on 02.03.2016.
 */
public class GameBoard  {
    private final int WIDTH, HEIGHT;
    protected double cellSize;
    protected double gridSpacing;
    private byte[][] gameBoard;
    private ConwaysRule activeRule;

    public GameBoard() {
        this(100, 100);
    }

    public GameBoard(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        gameBoard = new byte[WIDTH][HEIGHT];
        activeRule = new ConwaysRule();
    }

    public GameBoard(byte[][] gameboard) {
        WIDTH = -1;
        HEIGHT = -1;
        this.gameBoard = gameboard;
        activeRule = new ConwaysRule();
    }

    public void nextGen() {
        countNeigh();
        checkRules(activeRule);
    }

    /**
     * Inserts a byte 2D-array into the current gameboard at the given (y, x)
     * position.
     * <p>
     * For example: To insert boardFromFile to upper left corner of the current
     * gameboard, insert at position (0,0).
     * <p>
     * Elements from boardFromFile that exceeds the dimensios of the
     * current gameboard is not inserted.
     *
     *
     * @param boardFromFile bytearray to insert into the current gameboard.
     * @param y coordinate for where the first row is placed
     * @param x coordinate for where the first column is placed
     */
    public void insertArray(byte[][] boardFromFile, int y, int x) {
        for (int i = 0; i < boardFromFile.length; i++) {
            for (int j = 0; j < boardFromFile[i].length; j++) {
                if (i + y < gameBoard.length && j + x < gameBoard[y + i].length) {
                    if (i + y >= 0 && j + x >= 0) {
                        gameBoard[i + y][j + x] = boardFromFile[i][j];
                    }
                }
            }
        }
    }

    public int getArrayLength() {
        return gameBoard.length - 1;
    }

    public int getArrayLength(int i) {
        return gameBoard[i].length - 1;
    }

    public boolean getCellState(int x, int y) {
        return gameBoard[x][y] >= 64;
    }


    private void countNeigh() {

        //Goes through the board
        for (int i = 1; i < getArrayLength(); i++) {
            for (int j = 1; j < getArrayLength(i); j++) {

                //If cell is alive
                if (gameBoard[i][j] >= 64) {

                    //Goes through surrounding neighbours
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {

                            //To not count itself
                            if (!(k == 0 && l == 0)) {
                                gameBoard[i + k][j + l] += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    protected void checkRules(ConwaysRule activeRule) {
        for (int i = 1; i < (gameBoard.length - 1); i++) {
            for (int j = 1; j < (gameBoard[i].length - 1); j++) {
                if (gameBoard[i][j] != 0) {
                    gameBoard[i][j] = activeRule.setLife(gameBoard[i][j]);
                }
            }
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (byte row[] : gameBoard) {
            for (byte cell : row) {
                if (cell >= 64) {
                    result += 1;
                } else {
                    result += 0;
                }
            }
        }
        return result;
    }
}
