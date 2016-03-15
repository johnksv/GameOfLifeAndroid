package android.s305089.gameoflife.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.display.DisplayManager;
import android.s305089.gameoflife.board.GameBoard;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by s305089 on 02.03.2016.
 */
public class GameView extends View {
    GameBoard board = new GameBoard();
    Canvas canvas;
    Paint paint = new Paint();
    private double cellSize;

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
        for (int i = 1; i < board.getArrayLength(); i++) {
            for (int j = 1; j < board.getArrayLength(i); j++) {
                if(board.getCellState(i,j)){
                    canvas.drawRect(i*5,j*5,i*5+5,j*5+5,paint);
                }
            }
        }

        board.nextGen();
    }

    private void calculateCellSize() {
        if(board !=null){
            //cellSize = board.getArrayLength()/;
        }else{
            cellSize=1;
        }
    }

    /**
     * Constructs an new GameBoard with the given parameter
     * @param boardAsByte THe new gameboard. May contain values or not (64 for alive) (1 for dead)
     */
    public void setNewGameBoard(byte[][] boardAsByte){
        this.board = new GameBoard(boardAsByte);
        System.out.println(board);
        invalidate();
    }
}
