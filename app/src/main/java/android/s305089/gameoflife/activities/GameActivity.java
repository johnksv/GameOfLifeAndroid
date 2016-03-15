package android.s305089.gameoflife.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.s305089.gameoflife.R;
import android.s305089.gameoflife.board.BoardUsefullMethods;
import android.s305089.gameoflife.views.GameView;
import android.view.View;
import android.widget.Button;

public class GameActivity extends Activity {

    private GameView gameView;
    private Button btnNextGen, btnStartGame;
    private ValueAnimator animation = ValueAnimator.ofInt(0, 1).setDuration(250);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_viewer);
        gameView = (GameView) findViewById(R.id.game);
        btnNextGen = (Button) findViewById(R.id.btnNextGen);
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        initAnimation();

        Intent recivedIntent = getIntent();
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
                gameView.getBoard().nextGen();
                System.out.println(animation.getDuration());
            }
        });
    }

    public void handleStartBtn(View v) {

        if (!animation.isStarted()) {
            animation.start();
            btnStartGame.setText("Pause game");
            btnNextGen.setEnabled(false);
        } else if (animation.isPaused()) {
            animation.resume();
            btnStartGame.setText("Pause game");
            btnNextGen.setEnabled(true);
        } else {
            animation.pause();
            btnStartGame.setText("Start game");
            btnNextGen.setEnabled(true);
        }
    }

    public void handleNextGenBtn(View v) {
        gameView.invalidate();
        gameView.getBoard().nextGen();
    }
}

