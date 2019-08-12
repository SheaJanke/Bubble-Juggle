package com.cowbrain_games.bubble_juggle;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.LinkedList;

class MainGame {
    private int score = 0;
    private int lives = 5;
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private LinkedList<Ball> balls = new LinkedList<>();
    private long newBallTimer = System.currentTimeMillis();
    private Bitmap redX;
    private Bitmap greyX;

    MainGame(Bitmap redX, Bitmap greyX){
        this.redX = Bitmap.createScaledBitmap(redX,(int)X(150),(int)Y(150),true);
        this.greyX = Bitmap.createScaledBitmap(greyX,(int)X(150),(int)Y(150),true);

    }

    void tick(){
        for (Ball ball : balls) {
            ball.calculate(balls);
            if (ball.outOfBounds()) {
                balls.remove(ball);
                lives--;
            }
        }
        for (Ball ball : balls) {
            ball.updateVel();
        }
        if (System.currentTimeMillis() - newBallTimer > 3000 && spawnClear()) {
            newBallTimer = System.currentTimeMillis();
            addBall((int)X(125));
        }
        updateScore();
    }

    boolean isAlive(){
       return lives > 0;
    }

    void render(Canvas canvas){
       Paint paint = new Paint();
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3);
        paint.setTextSize(X(100));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        canvas.drawText("Score = " + score, X(500),Y(150),paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText("Score = " + score, X(500),Y(150),paint);
        for(int a = 0; a < 5; a++){
            if(a < 5-lives) {
                canvas.drawBitmap(redX, X(125) + X(150*a), Y(1800),paint);
            }else{
                canvas.drawBitmap(greyX, X(125) + X(150*a), Y(1800),paint);
            }
        }
        paint.setPathEffect(new DashPathEffect(new float[]{10,5},0));
        canvas.drawLine(X(0),Y(1000) ,X(1000), Y(1000),paint);
        for(Ball ball: balls) {
            ball.draw(canvas);
        }

    }
    private boolean spawnClear(){
        for(Ball ball:balls){
            if(ball.touchingBall(new Ball((int)X(125),X(200), Y(200), X(5), 0))){
                return false;
            }
        }
        return true;
    }

    private void addBall(int radius){
        Ball newBall = (new Ball(radius,X(200), Y(200), X(5), 0));
        balls.add(newBall);
    }

    void touched(MotionEvent e){
        if (e.getY() > Y(1000)) {
            for (Ball ball : balls) {
                if (ball.inArea((int) e.getX(), (int) e.getY())) {
                    ball.hit();
                }
            }
        }
    }

    private void updateScore(){
        for(int a = 1; a <= balls.size(); a++){
            score += a;
        }
    }

    int getScore(){
        return score;
    }

    void reset(){
        balls.clear();
        score = 0;
        lives = 5;
    }

    private float X(float x){
        return x*width/1000;
    }

    private float Y(float y){
        return y*height/2000;
    }

}
