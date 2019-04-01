package com.example.flightrace.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;

import com.example.flightrace.activity.GameActivity;

public abstract class Entity {
    private int x;
    private int y;
    private int w;
    private int h;
    private ShapeDrawable mShape;
    private Bitmap mImage;

    public Entity(int x, int y, int w, int h, int idImg) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap originalImg =  BitmapFactory.decodeResource(GameActivity.instance.getResources(), idImg, options);
        mImage = Bitmap.createScaledBitmap(originalImg, w, h, false);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ShapeDrawable getmShape() {
        return mShape;
    }

    public void setmShape(ShapeDrawable mShape) {
        this.mShape = mShape;
    }

    public Bitmap getmImage() {
        return mImage;
    }

    public void setmImage(Bitmap mImage) {
        this.mImage = mImage;
    }
}
