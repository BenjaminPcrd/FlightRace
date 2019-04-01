package com.example.flightrace.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.flightrace.R;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        Log.d("test", "onCreateMain");
        setContentView(R.layout.activity_main);

        final Button playButton = findViewById(R.id.playButton);
        final Button settingsButton = findViewById(R.id.settingsButton);
        final Button scoreButton = findViewById(R.id.scoreButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScoreActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test", "onStartMain");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("test", "onRestoreInstanceStateMain");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("test", "onRestartMain");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("test", "onResumeMain");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "onPauseMain");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("test", "onSaveInstanceStateMain");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test", "onStopMain");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "onDestroyMain");
    }
}
