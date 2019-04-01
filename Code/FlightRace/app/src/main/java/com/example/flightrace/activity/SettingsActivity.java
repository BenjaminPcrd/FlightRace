package com.example.flightrace.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.flightrace.R;

public class SettingsActivity extends AppCompatActivity {
    private String gameMode;
    private String difficulty;
    private int sensitivity;

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button validateButton = findViewById(R.id.validateButton);
        final ToggleButton easyButton = findViewById(R.id.easyButton);
        final ToggleButton normalButton = findViewById(R.id.normalButton);
        final ToggleButton hardButton = findViewById(R.id.hardButton);
        final ToggleButton fingerModeButton = findViewById(R.id.fingerModeButton);
        final ToggleButton accelerometerModeButton = findViewById(R.id.accelerometerModeButton);
        final TextView sensitivityLabel = findViewById(R.id.sensitivityLabel);
        final SeekBar sensitivitySeekBar = findViewById(R.id.sensitivitySeekBar);


        settings = getSharedPreferences("settings", MODE_PRIVATE);
        gameMode = settings.getString("gameMode", "accelerometer");
        difficulty = settings.getString("difficulty", "normal");
        sensitivity = settings.getInt("sensitivity", 15);

        settings.edit().putString("gameMode", gameMode).apply();
        settings.edit().putString("difficulty", difficulty).apply();
        settings.edit().putInt("sensitivity", sensitivity).apply();

        sensitivitySeekBar.setProgress(sensitivity - 1);

        switch(difficulty) {
            case "easy": easyButton.setChecked(true); break;
            case "normal": normalButton.setChecked(true); break;
            case "hard": hardButton.setChecked(true); break;
        }

        switch(gameMode) {
            case "finger":
                fingerModeButton.setChecked(true);
                sensitivityLabel.setEnabled(false);
                sensitivitySeekBar.setEnabled(false);
                break;
            case "accelerometer": accelerometerModeButton.setChecked(true); break;
        }

        String sensitivityLabelText = getString(R.string.sensitivity_label) + " " + ((sensitivitySeekBar.getProgress() + 1));
        sensitivityLabel.setText(sensitivityLabelText);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(SettingsActivity.this, MainActivity.class);

                settings.edit().putString("gameMode", gameMode).apply();
                settings.edit().putString("difficulty", difficulty).apply();
                settings.edit().putInt("sensitivity", sensitivity).apply();

                startActivity(mainActivity);
            }
        });

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyButton.setChecked(true);
                normalButton.setChecked(false);
                hardButton.setChecked(false);
                difficulty = "easy";
            }
        });

        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyButton.setChecked(false);
                normalButton.setChecked(true);
                hardButton.setChecked(false);
                difficulty = "normal";
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyButton.setChecked(false);
                normalButton.setChecked(false);
                hardButton.setChecked(true);
                difficulty = "hard";
            }
        });

        fingerModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fingerModeButton.setChecked(true);
                accelerometerModeButton.setChecked(false);
                sensitivityLabel.setEnabled(false);
                sensitivitySeekBar.setEnabled(false);
                gameMode = "finger";
            }
        });

        accelerometerModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fingerModeButton.setChecked(false);
                accelerometerModeButton.setChecked(true);
                sensitivityLabel.setEnabled(true);
                sensitivitySeekBar.setEnabled(true);
                gameMode = "accelerometer";
            }
        });


        sensitivitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String text = getString(R.string.sensitivity_label) + " " + ((i + 1));
                sensitivityLabel.setText(text);
                sensitivity = i + 1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        settings.edit().putString("gameMode", gameMode).apply();
        settings.edit().putString("difficulty", difficulty).apply();
        settings.edit().putInt("sensitivity", sensitivity).apply();
    }
}
