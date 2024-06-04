package com.example.lab5assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private static final int FPS = 60;
    private static int SPEED = 25;

    private static final int STATUS_PAUSED = 1;
    private static final int STATUS_START = 2;
    private static final int STATUS_OVER = 3;
    private static final int STATUS_PLAYING = 4;

    private GameView mGameView;
    private TextView mGameStatusText;
    private TextView mGameScoreText;
    private Button mGameBtn;

    private final AtomicInteger mGameStatus = new AtomicInteger(STATUS_START);

    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        final MediaPlayer mp1 = MediaPlayer.create(this , R.raw.updownleftright);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btnShow);
        registerForContextMenu(btn);

        mGameView = findViewById(R.id.game_view);
        mGameStatusText = findViewById(R.id.game_status);
        mGameBtn = findViewById(R.id.game_control_btn);
        mGameScoreText = findViewById(R.id.game_score);
        mGameView.init();
        mGameView.setGameScoreUpdatedListener(score -> {
           mHandler.post(() -> mGameScoreText.setText("Score: " + score));
        });

        findViewById(R.id.up_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                mGameView.setDirection(Direction.UP);
            }
        });
        findViewById(R.id.down_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                mGameView.setDirection(Direction.DOWN);
            }
        });
        findViewById(R.id.left_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                mGameView.setDirection(Direction.LEFT);
            }
        });
        findViewById(R.id.right_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                mGameView.setDirection(Direction.RIGHT);
            }
        });

        mGameBtn.setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                setGameStatus(STATUS_PAUSED);
            } else {
                setGameStatus(STATUS_PLAYING);
            }
        });

        setGameStatus(STATUS_START);

        ImageButton backToHome = (ImageButton)
                findViewById(R.id.backbuttongame);
        backToHome.setOnClickListener(new
                                         View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 mp1.start();
                                                 goToHome();
                                             }
                                         });
        ImageButton saveScore = (ImageButton)
                findViewById(R.id.savebuttongame);
        saveScore.setOnClickListener(new
                                              View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      mp1.start();
                                                      goToSaveGame();
                                                  }
                                              });

    }

    @Override
    protected void onPause() {

        super.onPause();
        if (mGameStatus.get() == STATUS_PLAYING) {
            setGameStatus(STATUS_PAUSED);
        }
    }

    private void setGameStatus(int gameStatus) {
        final MediaPlayer mp2 = MediaPlayer.create(this , R.raw.gameover);
        final MediaPlayer mp3 = MediaPlayer.create(this , R.raw.startgame);
        final MediaPlayer mp4 = MediaPlayer.create(this , R.raw.pause);
        int prevStatus = mGameStatus.get();
        mGameStatusText.setVisibility(View.VISIBLE);
        mGameBtn.setText("start");
        mGameStatus.set(gameStatus);
        switch (gameStatus) {
            case STATUS_OVER:
                mp2.start();
                mGameStatusText.setText("GAME OVER");
                break;
            case STATUS_START:
                mp3.start();
                mGameView.newGame();
                mGameStatusText.setText("START GAME");
                break;
            case STATUS_PAUSED:
                mGameStatusText.setText("GAME PAUSED");
                mp4.start();
                break;
            case STATUS_PLAYING:
                if (prevStatus == STATUS_OVER) {
                    mGameView.newGame();
                }
                startGame();
                mGameStatusText.setVisibility(View.INVISIBLE);
                mGameBtn.setText("pause");
                break;
        }
    }

    private void startGame() {
        final int delay = 1000 / FPS;
        new Thread(() -> {
            int count = 0;
            while (!mGameView.isGameOver() && mGameStatus.get() != STATUS_PAUSED) {
                try {
                    Thread.sleep(delay);
                    if (count % SPEED == 0) {
                        mGameView.next();
                        mHandler.post(mGameView::invalidate);
                    }
                    count++;
                } catch (InterruptedException ignored) {
                }
            }
            if (mGameView.isGameOver()) {
                mHandler.post(() -> setGameStatus(STATUS_OVER));
            }
        }).start();
    }

    private void goToHome() {
        Intent intent = new Intent(this,
                homepage.class);
        startActivity(intent);
    }

    private void goToSaveGame() {
        Intent intent = new Intent(this,
                SaveScore.class);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("PICK SPEED OF SNAKE");
        menu.add(0, v.getId(), 0, "slow");
        menu.add(0, v.getId(), 0, "medium");
        menu.add(0, v.getId(), 0, "fast");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "slow") {
            SPEED = 50;
        } else if (item.getTitle() == "medium") {
            SPEED = 25;

        } else if (item.getTitle() == "fast") {
            SPEED = 10;

        }
        return true;
    }

}