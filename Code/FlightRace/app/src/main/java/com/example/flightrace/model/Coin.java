package com.example.flightrace.model;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class Coin extends Obstacle {

    public Coin(int x, int y, int w, int h, int idImg, int speed) {
        super(x, y, w, h, idImg, speed);
        setmShape(new ShapeDrawable(new OvalShape()));
    }
}
