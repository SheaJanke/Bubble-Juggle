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
    private Ball HighScoreBall;
    private Ball menuBall;
    private LinkedList<Ball> balls = new LinkedList<>();

    void tick(){
        if(tickCounter== 38){
            balls.add(HighScoreBall);
        }
        if(tickCounter == 76){
            balls.add(menuBall);
        }
        for(Ball ball: balls){
            ball.calculate(balls);
            if(ball.getY() >= height-(float)ball.getRadius()){
                ball.setNewVelY(-ball.getVelY());
                ball.setX(X(500));
                ball.setY(Y(2000)-(int)X(160));
            }
        }
        for(Ball ball: balls){
            ball.updateVel();
        }
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
        paint.setColor(Color.BLUE);
        canvas.drawText("JUGGLE", X(500), Y(500), paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.WHITE);
        canvas.drawText("BUBBLE", X(500), Y(300), paint);
        canvas.drawText("JUGGLE", X(500), Y(500), paint);
        paint.setStyle(Paint.Style.FILL);
        for(int a = 0;a < balls.size(); a++){
            paint.setARGB(ballColors[a][0],ballColors[a][1],ballColors[a][2],ballColors[a][3]);
            canvas.drawCircle(balls.get(a).getX(),balls.get(a).getY(),balls.get(a).getRadius(),paint);
        }
        paint.setColor(Color.WHITE);
        paint.setTextSize(X(100));
        canvas.drawText("PLAY", balls.get(0).getX(),balls.get(0).getY() + Y(30),paint);
        if(balls.size() >=2){
            paint.setTextSize(X(80));
            canvas.drawText("HIGH", balls.get(1).getX(),balls.get(1).getY()-Y(10),paint);
            canvas.drawText("SCORES", balls.get(1).getX(),balls.get(1).getY()+Y(60),paint);
            if(balls.size() >=3){
                canvas.drawText("HOW TO", balls.get(2).getX(),balls.get(2).getY()+Y(10),paint);
                canvas.drawText("PLAY", balls.get(2).getX(),balls.get(2).getY()+Y(80),paint);
            }
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setTextSize(X(100));
        canvas.drawText("PLAY", balls.get(0).getX(),balls.get(0).getY() + Y(30),paint);
        if(balls.size() >=2){
            paint.setTextSize(X(80));
            canvas.drawText("HIGH", balls.get(1).getX(),balls.get(1).getY()-Y(10),paint);
            canvas.drawText("SCORES", balls.get(1).getX(),balls.get(1).getY()+Y(60),paint);
            if(balls.size() >=3){
                canvas.drawText("HOW TO", balls.get(2).getX(),balls.get(2).getY()+Y(10),paint);
                canvas.drawText("PLAY", balls.get(2).getX(),balls.get(2).getY()+Y(80),paint);
            }
        }
    }

    void reset(){
        balls.clear();
        tickCounter = 0;
        Ball playBall = new Ball((int)X(160), X(500),Y(1000),X(11.45f),0);
        HighScoreBall = new Ball((int)X(160), X(500),Y(1000),X(11.45f),0);
        menuBall = new Ball((int)X(160), X(500),Y(1000),X(11.45f),0);
        balls.add(playBall);
    }

    void touched(MotionEvent e, GameView gameView, MainGame mainGame, HighScoreScreen highScoreScreen){
        if (tickCounter > 20) {
            if (balls.get(0).inArea((int) e.getX(), (int) e.getY())) {
                mainGame.reset();
                gameView.setGameState(1);
            }
            if (balls.size() >= 2) {
                if (balls.get(1).inArea((int) e.getX(), (int) e.getY()))
                    gameView.setGameState(3);
                    highScoreScreen.reset();
            }
            if (balls.size() >= 3) {
                if (balls.get(2).inArea((int) e.getX(), (int) e.getY())) {
                    gameView.setGameState(4);
                }
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
