package android.s305089.gameoflife.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.s305089.gameoflife.R;
import android.s305089.gameoflife.board.BoardUsefullMethods;
import android.s305089.gameoflife.board.Board;
import android.s305089.gameoflife.views.GameView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

public class SimulationActivity extends Activity {

    private GameView gameView;
    private Board board;
    private Button btnStartGame;
    private TextView labelSpeed;
    private SeekBar seekBarSpeed;
    private ZoomControls zoomControls;

    private ValueAnimator animation = ValueAnimator.ofInt(0, 1).setDuration(250);
    private GestureDetector gestureDetector;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simulation);

        initVariables();
        initListners();
        initAnimation();
        initGameBoard();
        toast  = Toast.makeText(SimulationActivity.this, "", Toast.LENGTH_SHORT);
    }

    private void initVariables() {
        gameView = (GameView) findViewById(R.id.game);
        labelSpeed = (TextView) findViewById(R.id.labelSpeed);
        seekBarSpeed = (SeekBar) findViewById(R.id.seekBarSpeed);
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        zoomControls = (ZoomControls) findViewById(R.id.zoomControls);
        gestureDetector = new GestureDetector(this, new GameViewGestures());
        board = gameView.board;
    }

    private void initListners() {
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        });

        seekBarSpeed.setMax(1000);
        labelSpeed.setText("Speed: " + animation.getDuration() + "ms");
        seekBarSpeed.setProgress((int) animation.getDuration());

        seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if(progress ==0){
                        progress += 2;
                    }
                    animation.setDuration(progress);
                    labelSpeed.setText("Speed: " + animation.getDuration() + "ms");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.setCellSize(board.getCellSize() + 5);
                gameView.invalidate();
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (board.getCellSize() > 5) {
                    board.setCellSize(board.getCellSize() - 5);
                } else {
                    showToast("Kan ikke zoome lengre ut.");
                }
                gameView.invalidate();
            }
        });
    }

    private void initAnimation() {
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                gameView.invalidate();
                board.nextGen();
            }
        });
    }

    private void initGameBoard() {
        Intent receivedIntent = getIntent();
        byte[][] newGameBoard = (byte[][]) receivedIntent.getSerializableExtra("qrGameBoard");
        BoardUsefullMethods.setOnesTo64(newGameBoard);
        board.insertArray(newGameBoard, 1, 1);
    }

    public void handleStartBtn(View v) {

        if (!animation.isStarted()) {
            animation.start();
            btnStartGame.setText("Pause game");
        } else if (animation.isPaused()) {
            animation.resume();
            btnStartGame.setText("Pause game");
        } else {
            animation.pause();
            btnStartGame.setText("Start game");
        }
    }

    private void showToast(String message) {
        toast.setText(message);
        toast.show();
    }

    private class GameViewGestures extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!board.setCellState(e1.getY(), e1.getX(), true) || !board.setCellState(e2.getY(), e2.getX(), true)) {
                showToast("Du klikket utenfor spillbrettet..");
            }
            gameView.invalidate();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            double x = event.getX();
            double y = event.getY();
            if (!board.setCellState(y, x, !board.getCellState(y, x, 0, 0))) {
                showToast("Du klikket utenfor spillbrettet..");
            }
            gameView.invalidate();
            return true;
        }
    }
}