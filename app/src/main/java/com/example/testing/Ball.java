package com.example.testing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;


class Ball {
    private int radius;
    private float x;
    private float y;
    private double velX;
    private double velY;
    private double newVelX;
    private double newVelY;
    private double accel;
    private Canvas canvas;
    private int[] ballColors = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW};
    private int color;

    Ball(int radius){
        this.radius = radius;
        x = 300f;
        y = 300f;
        velX = 5;
        velY = 0;
        accel = 0.5;
        newVelX = velX;
        color = (int)(Math.random()* ballColors.length);
    }

    void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

    void calculate(LinkedList<Ball> others){
        y += velY;
        x += velX;
        newVelY = velY + accel;
        if(x < radius/2.0){
            x = radius/2.0f;
            newVelX = -velX/1.25;
        }
        if(x > canvas.getWidth() - radius/2){
            x = canvas.getWidth() - radius/2f;
            newVelX = -velX/1.25;
        }
        for(Ball other: others){
            if(other.getX() != x && other.getY() != y){
                if(touchingBall(other) && color != other.getColor()){
                    double totalVelY = Math.abs(velY) + Math.abs(other.getVelY());
                    if(x > other.getX() && velX < 0){
                        newVelX = -velX/2;
                    }
                    if(x < other.getX() && velX > 0){
                        newVelX = -velX/2;
                    }
                    if(y > other.getY() && velY < 0){
                        newVelY += (3.0/5)*totalVelY;
                    }
                    if(y < other.getY() && velY > 0){
                        newVelY = -(3.0/5)*totalVelY;

                    }
                    correctPosition(other);
                }
            }
        }
    }

    void updateVel(){
        velX = newVelX;
        velY = newVelY;
    }

    void draw(){
        Paint paint = new Paint();
        paint.setColor(ballColors[color]);
        paint.setTextSize(40);
        canvas.drawCircle(x,y,radius,paint);
    }


    boolean inArea(int X, int Y){
        int a = (int)x;
        int b = (int)y;
        return (X - a) * (X - a) + (Y - b) * (Y - b) < radius * radius;
    }

    void hit(){
        velX += (Math.random()-0.5)*25;
        velY = -40;
    }

    private float getX() {
        return x;
    }

    private float getY() {
        return y;
    }

    private double getVelY() {
        return velY;
    }

    private int getColor() {
        return color;
    }

    private boolean touchingBall(Ball other){
        return Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2) < Math.pow(radius * 2, 2);
    }

    private void correctPosition(Ball other){
        float difX = Math.abs(x-other.getX());
        float difY = Math.abs(y-other.getY());
        double angle = Math.atan(difY/difX);
        if(x > other.getX()){
            x = other.getX() + (float)Math.cos(angle)*radius*2;
        }else{
            x = other.getX() - (float)Math.cos(angle)*radius*2;
        }
        if(y > other.getY()){
            y = other.getY() + (float)Math.sin(angle)*radius*2;
        }else{
            y = other.getY() - (float)Math.sin(angle)*radius*2;
        }
    }

    boolean outOfBounds(){
        return y > canvas.getHeight()+radius;
    }

}
