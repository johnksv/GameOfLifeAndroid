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
import android.widget.Toast;
import android.widget.ZoomControls;

public class GameActivity extends Activity {

    private GameView gameView;
    private GameBoard gameBoard;
    private Button btnNextGen, btnStartGame;
    private ValueAnimator animation = ValueAnimator.ofInt(0, 1).setDuration(250);
    private float oldX = 0;
    private ZoomControls zoomControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = (GameView) findViewById(R.id.game);
        btnNextGen = (Button) findViewById(R.id.btnNextGen);
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        zoomControls = (ZoomControls) findViewById(R.id.zoomControls);

        gameBoard = gameView.getBoard();

        //TODO Set onZoomListners for view.
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBoard.setCellSize(gameBoard.getCellSize() + 5);
                gameView.invalidate();
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameBoard.getCellSize() > 0 ) {
                    gameBoard.setCellSize(gameBoard.getCellSize() - 5);
                }else{
                    Toast.makeText(GameActivity.this, "Can not zoom longer out", Toast.LENGTH_SHORT).show();
                }
                gameView.invalidate();
            }
        });

        initAnimation();

        Intent receivedIntent = getIntent();
        byte[][] newGameBoard = (byte[][]) receivedIntent.getSerializableExtra("qrGameBoard");
        BoardUsefullMethods.setOnesTo64(newGameBoard);
        gameView.setNewGameBoard(newGameBoard);
    }


    private void initAnimation() {
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                gameView.invalidate();
                gameBoard.nextGen();
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
        gameBoard.nextGen();
    }
}

