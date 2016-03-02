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

    Canvas canvas;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        canvas = new Canvas();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint paint =  new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawLine(0,0,500,500, paint);
        canvas.drawRect(50,50,500,500,paint);
        System.out.println("DRAW ME BITCH!");


    }
}
