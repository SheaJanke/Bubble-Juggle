package com.example.testing;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
    private int radius;
    private float x;
    private float y;
    private int velX;
    private int velY;
    private double accel;

    public Ball(int radius){
        this.radius = radius;
        x = 100f;
        y = 100f;
        velX = 0;
        velY = 0;
        accel = 1;
    }

    public void update(){
        velY += accel;
        y += velY;
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawCircle(x,y,radius,paint);
    }
}
