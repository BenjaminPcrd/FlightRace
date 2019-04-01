package com.example.flightrace.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.flightrace.model.Game;
import com.example.flightrace.model.GameLoop;
import com.example.flightrace.R;
import com.example.flightrace.model.ScoreManager;
import com.example.flightrace.view.Painter;

import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity implements SensorEventListener {
    public static GameActivity instance;
    Painter painter;
    private Game game;
    private GameLoop loop;
    private String gameMode;
    String difficulty;
    int sensitivity;

    SharedPreferences settings;

    SensorManager mSensorManager;
    Sensor mAccelerometer;

    private Chronometer chrono;
    private long time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        Log.d("test", "onCreateGame");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            this.getSupportActionBar().hide();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_game);

        settings = getSharedPreferences("settings", MODE_PRIVATE);
        gameMode = settings.getString("gameMode", "accelerometer");
        difficulty = settings.getString("difficulty", "normal");
        sensitivity = settings.getInt("sensitivity", 15);

        chrono = findViewById(R.id.chrono);

        painter = findViewById(R.id.painter);
        game = new Game(difficulty);
        painter.initPainter(game);
        loop = new GameLoop(painter, game);

        chrono.start();
        loop.start();

        painter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(gameMode.equals("finger") && !loop.isPaused()) {
                    game.updatePlayerFinger((int)event.getX());
                }
                return true;
            }
        });

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        game.setGameListener(new Game.GameListener() {
            @Override
            public void onLost() {
                runOnUiThread(new Runnable() { //sinon ça met l'exception "Only the original thread that created a view hierarchy can touch its views"
                    @Override
                    public void run() {
                        ImageView imageView = findViewById(R.id.explosionAnimImg);
                        imageView.setBackgroundResource(R.drawable.explosion_animation);
                        AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getBackground();
                        imageView.setX((game.getPlayer().getX() - game.getPlayer().getW()) - imageView.getBackground().getIntrinsicWidth());
                        imageView.setY((game.getPlayer().getY() - game.getPlayer().getH()) - imageView.getBackground().getIntrinsicHeight() );
                        frameAnimation.start();

                        chrono.stop();
                        findViewById(R.id.pauseButton).setVisibility(View.INVISIBLE);
                        findViewById(R.id.nbCoinLabel).setVisibility(View.INVISIBLE);
                        findViewById(R.id.coinImageView).setVisibility(View.INVISIBLE);
                        findViewById(R.id.chrono).setVisibility(View.INVISIBLE);

                        getLayoutInflater().inflate(R.layout.activity_game_lost, (ViewGroup)findViewById(R.id.constraintRoot));
                        TextView score = findViewById(R.id.scoreLabelValue);

                        findViewById(R.id.tryAgainButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });

                        findViewById(R.id.backToMenuButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(GameActivity.this, MainActivity.class));
                            }
                        });

                        int chronoMillis = (int)(SystemClock.elapsedRealtime() - chrono.getBase());
                        game.setScore(((game.getNbCoins()+1) * chronoMillis)/100);
                        score.setText(game.getScore()+"");

                        List<Integer> scores = ScoreManager.loadScores();
                        scores.add(game.getScore());
                        ScoreManager.saveScores(scores);
                    }
                });
            }

            @Override
            public void onCoin() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final TextView nbCoinLabel = findViewById(R.id.nbCoinLabel);
                        nbCoinLabel.setText(game.getNbCoins()+"");
                    }
                });
            }
        });

    }


    public void onPauseButtonClicked(View view) {
        ToggleButton pauseButton = (ToggleButton) view;
        Log.d("test", loop.isPaused()+"");
        if(pauseButton.isChecked()) {
            Log.d("test", "PAUSE");
            pauseButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_play));
            time = SystemClock.elapsedRealtime() - chrono.getBase();
            chrono.stop();
            loop.setPaused(true);
        } else {
            Log.d("test", "PLAY");
            pauseButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_pause));
            chrono.setBase(SystemClock.elapsedRealtime() - time);
            chrono.start();
            loop.setPaused(false);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(gameMode.equals("accelerometer") && !loop.isPaused()) {
            loop.sensorChanged(event.values[0], sensitivity);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("test", accuracy + "onAccuracyChanged");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test", "onStartGame");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("test", "onRestoreInstanceStateGame");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("test", "onRestartGame");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("test", "onResumeGame");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "onPauseGame");
        if(!loop.isPaused()) {
            findViewById(R.id.pauseButton).performClick();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("test", "onSaveInstanceStateGame");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test", "onStopGame");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "onDestroyGame");
        loop.interrupt();
    }
}
