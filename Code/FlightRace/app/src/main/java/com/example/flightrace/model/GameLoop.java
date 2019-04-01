package com.example.flightrace.model;

import android.util.Log;

import com.example.flightrace.view.Painter;

public class GameLoop extends Thread {
    private Painter painter;
    private Game game;
    private float interval;
    private static final int FRAMERATE = 60;
    private boolean paused;

    private float sensorValue;

    public GameLoop(Painter painter, Game game) {
        this.painter = painter;
        this.game = game;
        interval = 1000.0f / FRAMERATE;
        paused = false;
    }

    public void sensorChanged(float val, int sensitivity) {
        sensorValue = val * sensitivity;
    }

    @Override
    public void run() {
        while(!game.isLost()) {

            float time = System.currentTimeMillis();

            if(!paused) {
                game.updatePlayerSensor(sensorValue);
                painter.paintPlayer(game);
                game.updateObstacles();
                painter.paintObstacle(game);
                game.updateCoins();
                painter.paintCoin(game);
                painter.paintBackground();
                if(painter.collideObstacle()) {
                    game.setLost(true);
                }
                if(painter.collideCoin()) {
                    game.setNbCoins(game.getNbCoins() + 1);
                }
                painter.postInvalidate();
            }

            time = System.currentTimeMillis() - time;

            if(time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException e) {
                    Log.d("test", "INTERRUPTED");
                    break;
                }
            }
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
