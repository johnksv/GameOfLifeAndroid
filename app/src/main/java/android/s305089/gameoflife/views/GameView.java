package android.s305089.gameoflife.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.s305089.gameoflife.board.Board;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by s305089 on 02.03.2016.
 */
public class GameView extends View {
    public Board board;
    private Paint paint = new Paint();
    private boolean firstRun = true;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        board = new Board(160, 160);
        board.setCellSize(20);
        paint.setColor(Color.BLACK);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstRun) {
            //If not called, this.getWidht will retun zero.
            calculateCellSize();
            firstRun = false;
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


    private void calculateCellSize() {
        byte[][] boundedBoard = board.getBoundingBoxBoard();
        if (board != null) {
            board.setCellSize(Math.floor(this.getWidth() / (boundedBoard.length + 2)));
        } else {
            board.setCellSize(10);
        }
    }
}


