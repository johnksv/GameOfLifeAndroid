package android.s305089.gameoflife.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.s305089.gameoflife.board.GameBoard;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by s305089 on 02.03.2016.
 */
public class GameView extends View {
    private GameBoard board = new GameBoard();
    private Paint paint = new Paint();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (board == null) {
            board = new GameBoard(new byte[this.getHeight() / 10][this.getWidth() / 10]);
            board.setCellSize(20);
        }
        float cellSize = (float) board.getCellSize();
        canvas.drawColor(Color.WHITE);
        for (int i = 1; i <= board.getArrayLength(); i++) {
            for (int j = 1; j <= board.getArrayLength(i); j++) {
                if (board.getCellState(i, j)) {
                    canvas.drawRect(j * cellSize, i * cellSize, j * cellSize + cellSize, i * cellSize + cellSize, paint);
                }
            }
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
        board.insertArray(boardAsByte, 1, 1);
    }

    public GameBoard getBoard() {
        return board;
    }

}


