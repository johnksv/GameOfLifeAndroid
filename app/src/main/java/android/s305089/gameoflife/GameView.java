package android.s305089.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by s305089 on 02.03.2016.
 */
public class GameView extends View {
    GameBoard board = new GameBoard();
    Canvas canvas;
    Paint paint = new Paint();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        canvas = new Canvas();
        paint.setColor(Color.YELLOW);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLUE);
        for (int i = 1; i < board.getArrayLength(); i++) {
            for (int j = 1; j < board.getArrayLength(i); j++) {
                if(board.getCellState(i,j)){
                    canvas.drawRect(i,j,i*100,j*100,paint);
                }
            }
        }

    }

    public void onDraw(Canvas canvas, Paint paint){

    }

}
