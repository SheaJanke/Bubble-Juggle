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
    private int[] ballColors = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW};
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
        for(Ball other: others){
            if(other.getX() != x && other.getY() != y){
                if(touchingBall(other) && color != other.getColor()){
                    if(x > other.getX() && velX < 0){
                        velX = -velX/1.25;
                    }
                    if(x < other.getX() && velX > 0){
                        velX = -velX/1.25;
                    }
                    if(y > other.getY() && velY < 0){
                        velY = -velY/1.25;
                    }
                    if(y < other.getY() && velY > 0){
                        velY = -velY/1.25;
                    }
                }
            }
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public boolean touchingBall(Ball other){
        if(Math.pow(other.getX()-x,2) + Math.pow(other.getY()-y,2) < Math.pow(radius*2,2)){
            return true;
        }
        return false;
    }

}
