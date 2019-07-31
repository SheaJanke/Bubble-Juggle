package com.example.testing;

import android.content.res.Resources;
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
            addBall(width / 8);
        }
        updateScore();
    }

    boolean isAlive(){
       return lives > 0;
    }

    void render(Canvas canvas){
       Paint paint = new Paint();
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setTextSize((float)80/1080*width);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        canvas.drawText("Score = " + score, (float)width/2,(float)height/12,paint);
        canvas.drawText("Lives = " + lives, (float)width/2, (float)height*11/12, paint);
        paint.setPathEffect(new DashPathEffect(new float[]{10,5},0));
        canvas.drawLine(0f,(float)(height/2) ,(float)width, (float)(height/2),paint);
        for(Ball ball: balls) {
            ball.draw(canvas);
        }

    }

    private boolean spawnClear(){
        for(Ball ball:balls){
            if(ball.touchingBall(new Ball(width/8,(float)width/6, (float)height/6, 5.0, 0))){
                return false;
            }
        }
        return true;
    }

    void addBall(int radius){
        Ball newBall = new Ball(radius,(float)width/6, (float)height/6, 5.0, 0);
        balls.add(newBall);
    }

    void touched(MotionEvent e){
        if (e.getY() > height / 2) {
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

}
