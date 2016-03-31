package android.s305089.gameoflife.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.s305089.gameoflife.board.GameBoard;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by s305089 on 02.03.2016.
 */
public class GameView extends View {
    private GameBoard board = new GameBoard();
    private Canvas canvas;
    private Paint paint = new Paint();
    private float cellSize;
    private int spacing = 5;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        canvas = new Canvas();
        paint.setColor(Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateCellSize();

        canvas.drawColor(Color.WHITE);
        for (int i = 1; i <= getBoard().getArrayLength(); i++) {
            for (int j = 1; j <= getBoard().getArrayLength(i); j++) {
                if (getBoard().getCellState(i, j)) {
                    canvas.drawRect(j * cellSize, i * cellSize, j * cellSize + cellSize + spacing, i * cellSize + cellSize + spacing, paint);
                }
            }
        }
    }

    private void calculateCellSize() {
        if (getBoard() != null) {
            int a = this.getWidth();
            cellSize = (float) Math.floor(this.getWidth() / (getBoard().getArrayLength() + 2 * spacing));
        } else {
            cellSize = 10;
        }
    }

    /**
     * Constructs an new GameBoard with the given parameter.
     * Also creates an container box around the array, for drawing purposes.
     *
     * @param boardAsByte THe new gameboard. May contain values or not (64 for alive) (1 for dead)
     */
    public void setNewGameBoard(byte[][] boardAsByte) {
        int longestRowLength = 0;
        for (byte[] b : boardAsByte) {
            if (b.length > longestRowLength) {
                longestRowLength = b.length;
            }
        }

        GameBoard newBoard = new GameBoard(new byte[boardAsByte.length + 2][longestRowLength + 2]);
        newBoard.insertArray(boardAsByte, 0, 0);
        this.board = newBoard;
    }

    public GameBoard getBoard() {
        return board;
    }

}
