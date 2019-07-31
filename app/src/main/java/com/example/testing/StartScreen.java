package com.example.testing;

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
    private Ball playBall = new Ball(width/6, 100f, 100f, 5.0, 0);
    private LinkedList<Ball> balls = new LinkedList<>();

    void tick(){
        for (Ball ball : balls) {
            ball.calculate(balls);
            if(ball.getY() > height-ball.getRadius()/2){
                ball.setNewVelY(-ball.getVelY());
            }
        }
        for (Ball ball : balls) {
            ball.updateVel();
        }
    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        canvas.drawColor(Color.WHITE);
        paint.setTextSize((float)120/1080*width);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        paint.setColor(Color.BLUE);
        canvas.drawText("BUBBLE", (float)width/2,(float)(height/5.0),paint);
        canvas.drawText("JUGGLE", (float)width/2,(float)(height/4.0),paint);

        for(Ball ball:balls){
            ball.draw(canvas);
        }
        paint.setColor(Color.BLACK);
        canvas.drawText("PLAY", balls.get(0).getX(),balls.get(0).getY() + (float)height/50,paint);
    }

    void reset(){
        balls.clear();
        balls.add(playBall);
    }

    void touched(MotionEvent e, GameView gameView, MainGame mainGame){
        if(balls.get(0).inArea((int)e.getX(), (int)e.getY())){
            gameView.setGameState(1);
            mainGame.reset();
        }
    }
}
