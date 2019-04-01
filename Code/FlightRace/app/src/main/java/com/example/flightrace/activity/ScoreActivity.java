package com.example.flightrace.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.flightrace.R;
import com.example.flightrace.model.ScoreManager;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    SharedPreferences scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        List scores = ScoreManager.loadScores();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scores);
        ListView scoreList = findViewById(R.id.scoreList);
        scoreList.setAdapter(adapter);

        if(scores.size() == 0) {
            findViewById(R.id.deleteScoreButton).setVisibility(View.INVISIBLE);
        }

    }

    public void onDeleteScoreButton(View view) {
        List scores = new ArrayList();
        ScoreManager.saveScores(scores);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scores);
        ListView scoreList = findViewById(R.id.scoreList);
        scoreList.setAdapter(adapter);

        findViewById(R.id.deleteScoreButton).setVisibility(View.INVISIBLE);
    }

    public void onBackToMenuButton(View view) {
        startActivity(new Intent(ScoreActivity.this, MainActivity.class));
    }
}
