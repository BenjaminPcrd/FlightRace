package com.example.flightrace.model;

import android.content.Context;
import android.util.Log;

import com.example.flightrace.activity.MainActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager implements Serializable {
    public static void saveScores(List<Integer> scores) {
        try {
            FileOutputStream file = MainActivity.instance.openFileOutput("scores", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(scores);
            oos.close();
            Log.d("test", "Scores saved");
        } catch (IOException e) {
            Log.d("test", "IO out");
            e.printStackTrace();
        }
    }

    public static List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();
        try {
            FileInputStream file = MainActivity.instance.openFileInput("scores");
            ObjectInputStream ois = new ObjectInputStream(file);
            scores = (List<Integer>)ois.readObject();
            ois.close();
            file.close();
            Log.d("test", "Scores loaded");
        } catch (IOException e) {
            Log.d("test", "IO in");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d("test", "classNotFound in");
        }
        Collections.sort(scores, Collections.<Integer>reverseOrder());
        return scores;
    }
}
