package com.example.flightrace.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.example.flightrace.model.Coin;
import com.example.flightrace.model.Game;
import com.example.flightrace.model.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class Painter extends View {
    //Tolérance des collisisons
    private static final int PLAYER_SIZE_OFFSET = 50;
    private static final int PLAYER_Y_OFFSET = 0;

    private static final int OBSTACLE_WIDTH_OFFSET = 80;
    private static final int OBSTACLE_HEIGHT_OFFSET = 180;
    private static final int OBSTACLE_Y_OFFSET = 200;

    private static final int PLAYER_BOTTOM_OFFSET = 600;

    private List<ShapeDrawable> points;
    private Game theGame;


    private int windowWidth;
    private int windowsHeight;

    public Painter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        points = new ArrayList<>();
        this.windowWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.windowsHeight = context.getResources().getDisplayMetrics().heightPixels;

        generateBackground(200, 15, 5, 107);
    }

    public int randomIntWindow() {
        return (int) (100 + (Math.random() * ((windowWidth - 100) - 100))); // Min + (Math.random() * (Max - Min))
    }


    public void initPainter(Game game) {
        //game.getPlayer().getmShape().getPaint().setColor(Color.BLUE);
        game.getPlayer().getmShape().getPaint().setColor(INVISIBLE);
        game.getPlayer().setX(windowWidth/2);
        game.getPlayer().setY(windowsHeight - PLAYER_BOTTOM_OFFSET);

        for(Obstacle o : game.getObstacles()) {
            //o.getmShape().getPaint().setColor(Color.RED);
            o.getmShape().getPaint().setColor(INVISIBLE);
            o.setX(randomIntWindow());
        }

        for(Coin c : game.getCoins()) {
            //c.getmShape().getPaint().setColor(Color.YELLOW);
            c.getmShape().getPaint().setColor(INVISIBLE);
            c.setX(randomIntWindow());
        }

        theGame = game;
    }

    private void generateBackground(int nbPoints, int r, int g, int b) {
        super.setBackgroundColor(Color.rgb(r, g, b));
        for(int i = 0; i < nbPoints; i++) {
            int x = (int) (Math.random()*windowWidth);
            int y = (int) (Math.random()*windowsHeight);
            ShapeDrawable p = new ShapeDrawable(new OvalShape());
            p.getPaint().setColor(Color.WHITE);
            int size = (int) (2 + (Math.random() * (10 - 2))); // Min + (Math.random() * (Max - Min))
            p.setBounds(x, y, x + size, y + size);
            points.add(p);
        }
    }

    public void paintBackground() {
        for(ShapeDrawable p : points) {
            int left = p.getBounds().left;
            int top = p.getBounds().top;
            int right = p.getBounds().right;
            int bottom = p.getBounds().bottom;

            p.setBounds(left, top + 8, right, bottom + 8);

            if(p.getBounds().top > windowsHeight) {
                int size = (int) (2 + (Math.random() * (10 - 2))); // Min + (Math.random() * (Max - Min))
                p.setBounds(left, 0, left + size, 0 + size);
            }
        }
    }

    public void paintPlayer(Game game) {
        int x = game.getPlayer().getX();
        int y = game.getPlayer().getY();
        int w = game.getPlayer().getW();
        int h = game.getPlayer().getH();

        int left = (x - w/2) + PLAYER_SIZE_OFFSET;
        int top = ((y - h/2) + PLAYER_SIZE_OFFSET) - PLAYER_Y_OFFSET;
        int right = (x + w/2) - PLAYER_SIZE_OFFSET;
        int bottom = ((y + h/2) - PLAYER_SIZE_OFFSET) - PLAYER_Y_OFFSET;

        if(x <= 0) {
            game.getPlayer().setX(0);
        } else if(x >= windowWidth) {
            game.getPlayer().setX(windowWidth);
        } else {
            game.getPlayer().getmShape().setBounds(left, top, right, bottom);
        }
    }

    public void paintObstacle(Game game) {
        for(Obstacle o : game.getObstacles()) {
            int x = o.getX();
            int y = o.getY();
            int w = o.getW();
            int h = o.getH();

            if(y > windowsHeight + h) {
                y = 0 - h;
                o.setY(y);
                x = randomIntWindow();
                o.setX(x);
                o.setSpeed(game.randomObstacleSpeed());
            }
            int left = (x - w/2) + OBSTACLE_WIDTH_OFFSET;
            int top = ((y - h/2) + OBSTACLE_HEIGHT_OFFSET) + OBSTACLE_Y_OFFSET;
            int right = (x + w/2) - OBSTACLE_WIDTH_OFFSET;
            int bottom = ((y + h/2) - OBSTACLE_HEIGHT_OFFSET) + OBSTACLE_Y_OFFSET;

            o.getmShape().setBounds(left, top, right, bottom);
        }
    }

    public void paintCoin(Game game) {
        for(Coin c : game.getCoins()) {
            int x = c.getX();
            int y = c.getY();
            int w = c.getW();
            int h = c.getH();

            if(y > windowsHeight + h) {
                y = 0 - h;
                c.setY(y);
                x = randomIntWindow();
                c.setX(x);
                c.setSpeed(game.randomInt(Game.MIN_COIN_SPEED, Game.MAX_COIN_SPEED));
            }

            int left = x - w/2;
            int top = y - h/2;
            int right = x + w/2;
            int bottom = y + h/2;

            c.getmShape().setBounds(left, top, right, bottom);
        }
    }

    public boolean collideObstacle() {
        for(Obstacle o : theGame.getObstacles()) {
            int ox = (o.getmShape().getBounds().left + o.getmShape().getBounds().right) / 2;
            int oy = (o.getmShape().getBounds().top + o.getmShape().getBounds().bottom) / 2;
            int or = (o.getmShape().getBounds().right - o.getmShape().getBounds().left)/2;

            int px = (theGame.getPlayer().getmShape().getBounds().left + theGame.getPlayer().getmShape().getBounds().right) / 2;
            int py = (theGame.getPlayer().getmShape().getBounds().top + theGame.getPlayer().getmShape().getBounds().bottom) / 2;
            int pr = (theGame.getPlayer().getmShape().getBounds().right - theGame.getPlayer().getmShape().getBounds().left) / 2;

            /*if (Rect.intersects(theGame.getPlayer().getmShape().getBounds(), o.getmShape().getBounds())) {
                Log.d("test", "COLLISION");
                return true;
            }*/

            if(Math.sqrt(Math.pow(ox - px, 2) + Math.pow(oy - py, 2)) < or + pr) {
                Log.d("test", "COLLISION");
                theGame.getObstacles().remove(o);
                theGame.getPlayer().getmImage().eraseColor(INVISIBLE);
                return true;
            }
        }
        return false;
    }

    public boolean collideCoin() {
        for(Coin c : theGame.getCoins()) {
            if (Rect.intersects(theGame.getPlayer().getmShape().getBounds(), c.getmShape().getBounds())) {
                Log.d("test", "COLLISION COIN");
                c.setY(-100);
                c.setX(randomIntWindow());
                c.setSpeed(theGame.randomInt(Game.MIN_COIN_SPEED, Game.MAX_COIN_SPEED));
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        for(ShapeDrawable p : points) {
            p.draw(canvas);
        }
        for(Obstacle o : theGame.getObstacles()) {
            o.getmShape().draw(canvas);
            canvas.drawBitmap(o.getmImage(), o.getmShape().getBounds().left - OBSTACLE_WIDTH_OFFSET, (o.getmShape().getBounds().top - OBSTACLE_WIDTH_OFFSET) - OBSTACLE_Y_OFFSET, null);
        }
        for(Coin c : theGame.getCoins()) {
            c.getmShape().draw(canvas);
            canvas.drawBitmap(c.getmImage(), c.getmShape().getBounds().left, c.getmShape().getBounds().top, null);
        }
        theGame.getPlayer().getmShape().draw(canvas);
        canvas.drawBitmap(theGame.getPlayer().getmImage(), theGame.getPlayer().getmShape().getBounds().left - PLAYER_SIZE_OFFSET, (theGame.getPlayer().getmShape().getBounds().top - PLAYER_SIZE_OFFSET) + PLAYER_Y_OFFSET, null);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
