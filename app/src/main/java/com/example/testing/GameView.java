package com.example.testing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.LinkedList;


public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    private long lastTime = System.currentTimeMillis();
    private long newBallTimer = System.currentTimeMillis();
    LinkedList<Ball> balls = new LinkedList<>();
    Context context;

    public GameView(Context context){
        super(context);
        this.context = context;

        getHolder().addCallback(this);
        addBall(100);
        thread = new MainThread(getHolder(), this, balls);
        setFocusable(true);

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
             retry = false;
        }
    }

    public void update(){
        for(Ball ball:balls){
            ball.update(balls);
            if(ball.outOfBounds()){
                balls.remove(ball);
            }
        }
        if(System.currentTimeMillis() - newBallTimer > 3000){
            newBallTimer = System.currentTimeMillis();
            addBall(100);
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            canvas.drawColor(Color.WHITE);
            for(Ball ball: balls) {
                ball.draw();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(System.currentTimeMillis() - lastTime > 100) {
            for (Ball ball : balls) {
                if (ball.inArea((int) e.getX(), (int) e.getY())) {
                    lastTime = System.currentTimeMillis();
                    ball.hit();

                }
            }
        }
        return true;
    }

    public void addBall(int radius){
        Ball newBall = new Ball(radius);
        balls.add(newBall);
    }

}
