package com.cowbrain_games.bubble_juggle;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.LinkedList;

class StartScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int[][] ballColors = {{255,255,0,0},{255,0,128,0},{255,0,0,255}};
    private int tickCounter = 0;
    private LinkedList<Ball> balls = new LinkedList<>();

    void tick() {
        tickCounter++;
    }

    void render(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(X(200));
        paint.setColor(Color.RED);
        canvas.drawText("BUBBLE", X(500), Y(300), paint);
        paint.setARGB(255,255,140,0);
        canvas.drawText("JUGGLE", X(500), Y(300)+X(205), paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(5));
        paint.setColor(Color.WHITE);
        canvas.drawText("BUBBLE", X(500), Y(300), paint);
        canvas.drawText("JUGGLE", X(500), Y(300)+X(205), paint);
        for(int a = 0;a < balls.size(); a++){
            paint.setStyle(Paint.Style.FILL);
            paint.setARGB(ballColors[a][0],ballColors[a][1],ballColors[a][2],ballColors[a][3]);
            canvas.drawCircle(balls.get(a).getX(),balls.get(a).getY(),balls.get(a).getRadius(),paint);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            canvas.drawCircle(balls.get(a).getX(),balls.get(a).getY(),balls.get(a).getRadius(),paint);
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(X(5));
        paint.setColor(Color.WHITE);
        paint.setTextSize(X(140));
        canvas.drawText("PLAY", balls.get(0).getX(),balls.get(0).getY() + X(45),paint);
        paint.setTextSize(X(90));
        canvas.drawText("HIGH", balls.get(1).getX(),balls.get(1).getY()-X(15),paint);
        canvas.drawText("SCORES", balls.get(1).getX(),balls.get(1).getY()+X(70),paint);
        canvas.drawText("HOW TO", balls.get(2).getX(),balls.get(2).getY(),paint);
        canvas.drawText("PLAY", balls.get(2).getX(),balls.get(2).getY()+X(85),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setTextSize(X(140));
        canvas.drawText("PLAY", balls.get(0).getX(),balls.get(0).getY() + X(45),paint);
        paint.setTextSize(X(90));
        canvas.drawText("HIGH", balls.get(1).getX(),balls.get(1).getY()-X(15),paint);
        canvas.drawText("SCORES", balls.get(1).getX(),balls.get(1).getY()+X(70),paint);
        canvas.drawText("HOW TO", balls.get(2).getX(),balls.get(2).getY(),paint);
        canvas.drawText("PLAY", balls.get(2).getX(),balls.get(2).getY()+X(85),paint);
    }

    void reset(){
        balls.clear();
        tickCounter = 0;
        balls.add(new Ball((int)X(200), X(500),Y(1000),X(11.38f),0));
        balls.add(new Ball((int)X(200), X(250),Y(1000)+X(450),X(11.38f),0));
        balls.add(new Ball((int)X(200), X(750),Y(1000)+X(450),X(11.38f),0));
    }

    void touched(MotionEvent e, GameView gameView, MainGame mainGame, HighScoreScreen highScoreScreen){
        if (tickCounter > 20) {
            if (balls.get(0).inArea((int) e.getX(), (int) e.getY())) {
                mainGame.reset();
                gameView.setGameState(1);
            }
            if (balls.get(1).inArea((int) e.getX(), (int) e.getY())){
                gameView.setGameState(3);
                highScoreScreen.reset();
            }
            if (balls.get(2).inArea((int) e.getX(), (int) e.getY())) {
                gameView.setGameState(4);
            }
        }
    }

    private float X(float x){
        return x*width/1000;
    }

    private float Y(float y){
        return y*height/2000;
    }
}
