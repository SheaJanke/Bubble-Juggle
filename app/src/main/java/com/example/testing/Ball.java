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
    private double velX;
    private double velY;
    private double newVelX;
    private double newVelY;
    private double accel;
    private int[] ballColors = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW};
    private int color;

    Ball(int radius, float startX, float startY, double startVelX, double startVelY){
        this.radius = radius;
        x = 300f;
        y = 300f;
        velX = 5;
        velY = 0;
        accel = 0.45/2160*height;
        newVelX = velX;
        color = (int)(Math.random()* ballColors.length);
        lastHit = System.currentTimeMillis();
    }

    void calculate(LinkedList<Ball> others){
        y += velY;
        x += velX;
        newVelY = velY + accel;
        if(x < radius/2.0){
            x = radius/2.0f;
            newVelX = -velX/1.25;
        }
        if(x > width - radius/2){
            x = width - radius/2f;
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
                        if(other.getVelY() < 0) {
                            newVelY = -(3.0 / 5) * totalVelY;
                        }else{
                            newVelY = -(3.0/5) * velY;
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
        paint.setShader(new RadialGradient(x,y,radius,Color.WHITE,ballColors[color],Shader.TileMode.REPEAT));
        canvas.drawCircle(x,y,radius-5,paint);

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

    float getY() {
        return y;
    }

    void setY(float y){
        this.y = y;
    }

    void setNewVelY(double newVelY){
        this.newVelY = newVelY;
    }

    int getRadius(){
        return radius;
    }

    double getVelY() {
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
}
