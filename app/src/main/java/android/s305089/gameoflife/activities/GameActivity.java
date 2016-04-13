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
import android.view.GestureDetector;
import android.view.MotionEvent;
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
    private GestureDetector gestureDetector;
    private GameViewGestures gameViewGestures = new GameViewGestures();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = (GameView) findViewById(R.id.game);
        btnNextGen = (Button) findViewById(R.id.btnNextGen);
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        zoomControls = (ZoomControls) findViewById(R.id.zoomControls);

        gestureDetector = new GestureDetector(this, new GameViewGestures());
        gameBoard = gameView.getBoard();

        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        });

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
                if (gameBoard.getCellSize() > 0) {
                    gameBoard.setCellSize(gameBoard.getCellSize() - 5);
                } else {
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

    class GameViewGestures extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            gameBoard.setCellState(e1.getY(), e1.getX(), true, 0, 0);
            gameBoard.setCellState(e2.getY(), e2.getX(), true, 0, 0);
            gameView.invalidate();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            double x = event.getX();
            double y = event.getY();
            gameBoard.setCellState(y, x, !gameBoard.getCellState(y, x, 0, 0), 0, 0);
            gameView.invalidate();
            return true;
        }
    }
}

