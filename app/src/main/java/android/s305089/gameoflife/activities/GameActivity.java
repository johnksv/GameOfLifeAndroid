package android.s305089.gameoflife.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.s305089.gameoflife.R;
import android.s305089.gameoflife.board.BoardUsefullMethods;
import android.s305089.gameoflife.board.GameBoard;
import android.s305089.gameoflife.views.GameView;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GameActivity extends Activity {

    private GameView gameView;
    private Button btnNextGen, btnStartGame;
    private ValueAnimator animation = ValueAnimator.ofInt(0, 1).setDuration(250);
    private Intent recivedIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_viewer);
        gameView = (GameView) findViewById(R.id.game);
        btnNextGen = (Button) findViewById(R.id.btnNextGen);
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        initAnimation();

        recivedIntent = getIntent();
        byte[][] newGameBoard = (byte[][]) recivedIntent.getSerializableExtra("qrGameBoard");
        BoardUsefullMethods.setOnesTo64(newGameBoard);
        gameView.setNewGameBoard(newGameBoard);

    }


    private void initAnimation() {
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                gameView.invalidate();
                System.out.println(animation.getDuration());
            }
        });
    }

    public void handleStartBtn(View v) {

        if (animation.isStarted()) {
            animation.pause();
            btnStartGame.setText("Start game");
            btnNextGen.setClickable(true);
        } else {
            animation.start();
            btnStartGame.setText("Pause game");
            btnNextGen.setClickable(false);
        }

    }

    public void handleNextGenBtn(View v) {
        gameView.invalidate();
    }

}

