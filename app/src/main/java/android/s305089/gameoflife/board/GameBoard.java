package android.s305089.gameoflife.board;

import java.io.Serializable;

/**
 * Created by s305089 on 02.03.2016.
 */
public class GameBoard {
    private final int WIDTH, HEIGHT;
    private double cellSize = 10;
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

    private void countNeigh() {
        for (int i = 1; i < getArrayLength(); i++) {
            for (int j = 1; j < getArrayLength(i); j++) {

                if (gameBoard[i][j] >= 64) {
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {

                            if (!(k == 0 && l == 0)) {
                                gameBoard[i + k][j + l] += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkRules(ConwaysRule activeRule) {
        for (int i = 1; i < (gameBoard.length - 1); i++) {
            for (int j = 1; j < (gameBoard[i].length - 1); j++) {
                if (gameBoard[i][j] != 0) {
                    gameBoard[i][j] = activeRule.setLife(gameBoard[i][j]);
                }
            }
        }
    }

    /**
     * Inserts a byte 2D-array into the current gameboard at the given (y, x)
     * position.
     * <p/>
     * For example: To insert boardFromFile to upper left corner of the current
     * gameboard, insert at position (0,0).
     * <p/>
     * Elements from boardFromFile that exceeds the dimensios of the
     * current gameboard is not inserted.
     *
     * @param boardFromFile bytearray to insert into the current gameboard.
     * @param y             coordinate for where the first row is placed
     * @param x             coordinate for where the first column is placed
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

    public byte[][] getBoundingBoxBoard() {
        int[] boundingBox = getBoundingBox();
        if ((boundingBox[1] - boundingBox[0] + 1) > 0 || (boundingBox[3] - boundingBox[2] + 1) > 0) {
            byte[][] board = new byte[boundingBox[1] - boundingBox[0] + 1][boundingBox[3] - boundingBox[2] + 1];

            for (int y = 0; y < board.length; y++) {
                for (int x = 0; x < board[y].length; x++) {
                    if (gameBoard[boundingBox[0] + y][x + boundingBox[2]] == 64) {
                        board[y][x] = 64;
                    } else {
                        board[y][x] = 0;
                    }
                }
            }
            return board;
        } else {
            return new byte[][]{{}};
        }
    }


    public int[] getBoundingBox() {
        int[] boundingBox = new int[4]; // minrow maxrow mincolumn maxcolumn
        boundingBox[0] = gameBoard.length;
        boundingBox[1] = 0;
        boundingBox[2] = Integer.MAX_VALUE;
        boundingBox[3] = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (gameBoard[i][j] != 64) {
                    continue;
                }
                if (i < boundingBox[0]) {
                    boundingBox[0] = i;
                }
                if (i > boundingBox[1]) {
                    boundingBox[1] = i;
                }
                if (j < boundingBox[2]) {
                    boundingBox[2] = j;
                }
                if (j > boundingBox[3]) {
                    boundingBox[3] = j;
                }
            }
        }
        return boundingBox;
    }

    public int getArrayLength() {
        return gameBoard.length - 1;
    }

    public int getArrayLength(int i) {
        return gameBoard[i].length - 1;
    }

    public void setCellState(int y, int x, boolean alive) {
        byte value = 0;
        if (alive) {
            value = 64;
        }
        if (y < gameBoard.length && y >= 0) {
            if (x < gameBoard[y].length && x >= 0) {
                gameBoard[y][x] = value;
            } else {
                System.err.println("x or y was not in gameboard.");
            }

        } else {
            System.err.println("x or y was not in gameboard.");
        }
    }

    public void setCellState(double y, double x, boolean alive, double offsetX, double offsetY) {
        /*
         * y is position of the first index of the matrix (column)
         * x is position of the second index of the matrix (row)
         */
        y = y / cellSize;
        x = x / cellSize;
        offsetY = offsetY / cellSize;
        offsetX = offsetX / cellSize;

        setCellState((int) Math.floor(y - offsetY), (int) Math.floor(x - offsetX), alive);
    }

    public boolean getCellState(int y, int x) {
        if (y < 1 || y >= gameBoard.length) {
            return false;
        }
        if (x < 1 || x >= gameBoard[y].length) {
            return false;
        }
        return gameBoard[y][x] >= 64;
    }

    public boolean getCellState(double y, double x, double offsetX, double offsetY) {

        /*
         * y is position of the first index of the matrix (column)
         * x is position of the second index of the matrix (row)
         */
        y = y / cellSize;
        x = x / cellSize;
        offsetY = offsetY / cellSize;
        offsetX = offsetX / cellSize;

        return getCellState((int) Math.floor(y - offsetY), (int) Math.floor(x - offsetX));
    }

    public double getCellSize() {
        return cellSize;
    }

    public void setCellSize(double cellSize) {
        if (cellSize < 0.2) {
            this.cellSize = 0.2;
        }
        this.cellSize = cellSize;
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
