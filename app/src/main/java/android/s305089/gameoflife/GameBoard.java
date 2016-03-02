package android.s305089.gameoflife;

/**
 * Created by s305089 on 02.03.2016.
 */
public class GameBoard {
    private final int WIDTH, HEIGHT;
    private byte[][] gameBoard;
    protected double cellSize;
    protected double gridSpacing;
    private ConwaysRule activeRule;

    public GameBoard() {
        this(100, 100);
    }

    public GameBoard(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        //gameBoard = new byte[WIDTH][HEIGHT];
        gameBoard = new byte[][]{
                {0, 0, 0, 0},
                {0, 64, 0, 0},
                {0, 0, 64, 0},
                {0, 0, 0, 0}};
        activeRule = new ConwaysRule();
    }

    public void nextGen() {
        countNeigh();
        checkRules(activeRule);
    }

    public void clearBoard() {
        for (int i = 1; i < getArrayLength(); i++) {
            for (int j = 1; j < getArrayLength(i); j++) {
                gameBoard[i][j] = 0;
            }
        }
    }

    public int getArrayLength() {
        return gameBoard.length - 1;
    }

    public int getArrayLength(int i) {
        return gameBoard[i].length - 1;
    }

    public byte[][] getGameBoard() {
        return gameBoard;
    }

    public void setCellState(double x, double y, boolean alive) {

        /*
         * y is position of the first index of the matrix (column)
         * x is position of the second index of the matrix (row)
         */
        y = y / (cellSize + gridSpacing);
        x = x / (cellSize + gridSpacing);
        byte value = 0;
        if (alive) {
            value = 64;
        }

        try {
            gameBoard[(int) y][(int) x] = value;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Click was outside array");
        }
    }

    protected void setGameBoard(Object gameBoard) {
        if (gameBoard instanceof byte[][]) {
            this.gameBoard = (byte[][]) gameBoard;
        }
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
            //result += "\n";
        }

        return result;
    }

}
