package com.example.flightrace.model;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class Obstacle extends Entity {
    private int speed;

    public Obstacle(int x, int y, int w, int h, int idImg, int speed) {
        super(x, y, w, h, idImg);
        this.speed = speed;
        setmShape(new ShapeDrawable(new OvalShape()));
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
