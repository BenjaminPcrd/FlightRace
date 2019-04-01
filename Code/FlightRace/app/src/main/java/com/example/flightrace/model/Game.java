package com.example.flightrace.model;


import com.example.flightrace.R;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int MIN_OBSTACLE_SPEED = 15;
    private static final int MAX_OBSTACLE_SPEED = 25;

    private static final float EASY_SPEED_COEFF = 1;
    private static final float NORMAL_SPEED_COEFF = 1.3f;
    private static final float HARD_SPEED_COEFF = 1.6f;

    public static final int MIN_COIN_SPEED = 15;
    public static final int MAX_COIN_SPEED = 25;

    private String difficulty;

    private Player player;
    private List<Obstacle> obstacles;
    private List<Coin> coins;
    private boolean lost;
    private int nbCoins;
    private int score;

    public interface GameListener {
        void onLost();
        void onCoin();
    }

    private GameListener listener;

    public Game(String difficulty) {
        this.difficulty = difficulty;
        player = new Player(0, 0, 200, 200, R.drawable.ship);
        obstacles = new ArrayList<>();
        coins = new ArrayList<>();
        lost = false;
        this.listener = null;
        nbCoins = 0;

        switch(difficulty) {
            case "easy":
                obstacles.add(new Obstacle(0, -1000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -2000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -3000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                break;
            case "normal":
                obstacles.add(new Obstacle(0, -1000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -2000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -3000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -1000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -2000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                break;
            case "hard":
                obstacles.add(new Obstacle(0, -1000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -2000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -3000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -1000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -2000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -3000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                obstacles.add(new Obstacle(0, -1000, 300, 500, R.drawable.meteor3, randomObstacleSpeed()));
                break;
        }

        coins.add(new Coin(0, -1000, 100, 100, R.drawable.coin, randomInt(MIN_COIN_SPEED, MAX_COIN_SPEED)));
    }

    public int randomInt(float min, float max) {
        return (int) (min + (Math.random() * (max - min))); // Min + (Math.random() * (Max - Min))
    }

    public int randomObstacleSpeed() {
        switch(difficulty) {
            case "easy": return randomInt((MIN_OBSTACLE_SPEED * EASY_SPEED_COEFF), (MAX_OBSTACLE_SPEED * EASY_SPEED_COEFF));
            case "normal": return randomInt((MIN_OBSTACLE_SPEED * NORMAL_SPEED_COEFF), (MAX_OBSTACLE_SPEED * NORMAL_SPEED_COEFF));
            case "hard": return randomInt((MIN_OBSTACLE_SPEED * HARD_SPEED_COEFF), (MAX_OBSTACLE_SPEED * HARD_SPEED_COEFF));
        }
        return randomInt((MIN_OBSTACLE_SPEED * NORMAL_SPEED_COEFF), (MAX_OBSTACLE_SPEED * NORMAL_SPEED_COEFF));
    }

    public void setGameListener(GameListener listener) {
        this.listener = listener;
    }

    public void updatePlayerFinger(int x) {
        player.setX(x);
    }

    void updatePlayerSensor(float val) {
        player.setX((int)(player.getX() - val));
    }

    void updateObstacles() {
        for(Obstacle o : obstacles) {
            o.setY(o.getY() + o.getSpeed());
        }
    }
    void updateCoins() {
        for(Coin c : coins) {
            c.setY(c.getY() + c.getSpeed());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    boolean isLost() {
        return lost;
    }

    void setLost(boolean lost) {
        this.lost = lost;
        if(listener != null) {
            listener.onLost();
        }
    }

    public int getNbCoins() {
        return nbCoins;
    }

    void setNbCoins(int nbCoins) {
        this.nbCoins = nbCoins;
        if(listener != null) {
            listener.onCoin();
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
