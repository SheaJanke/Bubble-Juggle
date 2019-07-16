package com.example.testing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;


public class Ball {
    private int radius;
    private float x;
    private float y;
    private double velX;
    private double velY;
    private double accel;
    private Canvas canvas;
    private int[] ballColors = {Color.GREEN, Color.RED
            , Color.BLUE};
    private int color;

    public Ball(int radius){
        this.radius = radius;
        x = 100f;
        y = 100f;
        velX = 5;
        velY = 0;
        accel = 0.5;
        color = (int)(Math.random()* ballColors.length);
    }

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

    public void update(LinkedList<Ball> others){
        velY += accel;
        y += velY;
        x += velX;
        if((y > (canvas.getHeight() - radius/2))&&(velY >0)){
            y = canvas.getHeight()-(radius/2);
            velY = -(velY);
        }
        if(x < (0 + radius/2) || x > (canvas.getWidth() - radius/2)){
            velX = -velX;
        }
    }

    public void draw(){
        Paint paint = new Paint();
        paint.setColor(ballColors[color]);
        canvas.drawCircle(x,y,radius,paint);
    }


    public boolean inArea(int X, int Y){
        int a = (int)x;
        int b = (int)y;
        if((X-a)*(X-a) + (Y-b)*(Y-b) < radius * radius){
            return true;
        }else{
            return false;
        }
    }

    public void hit(){
        if(velY > 0){
            velY = -(velY*0.5 + 15);
            velX += (Math.random()-0.5)*15;
        }
    }
}
