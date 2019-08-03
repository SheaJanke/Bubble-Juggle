package com.example.testing;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import java.util.LinkedList;


class Ball {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int radius;
    private long lastHit;
    private float x;
    private float y;
    private float velX;
    private float velY;
    private float newVelX;
    private float newVelY;
    private float accel;
    private int[][] ballColors = {{255,255,0,0},{255,255,165,0},{255,255,255,0},{255,0,128,0},{255,0,0,255}};
    private int color;

    Ball(int radius, float startX, float startY, float startVelX, float startVelY){
        this.radius = radius;
        x = startX;
        y = startY;
        velX = startVelX;
        velY = startVelY;
        accel = Y(0.5f);
        newVelX = velX;
        color = (int)(Math.random()* ballColors.length);
        lastHit = System.currentTimeMillis();
    }

    void calculate(LinkedList<Ball> others){
        y += velY;
        x += velX;
        newVelY = velY + accel;
        if(x <= radius){
            x = radius;
            newVelX = -velX;
        }
        if(x >= width - radius){
            x = width - radius;
            newVelX = -velX;
        }
        for(Ball other: others){
            if(other.getX() != x && other.getY() != y){
                if(touchingBall(other) && color != other.getColor()){
                    float totalVelY = Math.abs(velY) + (float)Math.abs(other.getVelY());
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
                        if(other.getVelY() < 0) {
                            newVelY = -(3f / 5) * totalVelY;
                        }else{
                            newVelY = -(3f/5) * velY;
                        }
                    }
                    if(y > other.getY() && velY > 0){
                        newVelY += (1.0/5.0)*other.getVelY();
                    }
                    if(y < other.getY() && velY < 0){
                        newVelY += (3.0/5.0)*other.getVelY();
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

    void draw(Canvas canvas){
        Paint paint = new Paint();
        int c = Color.argb(ballColors[color][0],ballColors[color][1],ballColors[color][2],ballColors[color][3]);
        paint.setShader(new RadialGradient(x,y,radius,Color.WHITE,c,Shader.TileMode.REPEAT));
        canvas.drawCircle(x,y,radius,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        canvas.drawCircle(x,y,radius,paint);


    }


    boolean inArea(int X, int Y){
        int a = (int)x;
        int b = (int)y;
        return (X - a) * (X - a) + (Y - b) * (Y - b) < radius * radius;
    }

    void hit(){
        if(System.currentTimeMillis()-lastHit > 300) {
            lastHit = System.currentTimeMillis();
            velX += (Math.random() - 0.5) * 25;
            velY = -((float)30/2160*height);
        }
    }

    float getX() {
        return x;
    }

    void setX(float X){
        x = X;
    }

    void setY(float Y){
        y = Y;
    }

    float getY() {
        return y;
    }

    void setNewVelY(float newVelY){
        this.newVelY = newVelY;
    }

    int getRadius(){
        return radius;
    }

    float getVelY() {
        return velY;
    }

    private int getColor() {
        return color;
    }

    boolean touchingBall(Ball other){
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
        return y > height + radius;
    }

    private float X(float x){
        return x*width/1000;
    }

    private float Y(float y){
        return y*height/2000;
    }
}
