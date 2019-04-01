package com.example.flightrace.model;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class Player extends Entity {
    public Player(int x, int y, int w, int h, int idImg) {
        super(x, y, w, h, idImg);
        setmShape(new ShapeDrawable(new OvalShape()));
    }
}
