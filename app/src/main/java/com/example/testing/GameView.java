package com.example.testing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.LinkedList;


public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    private long lastTime = System.currentTimeMillis();
    LinkedList<Ball> balls = new LinkedList<Ball>();
    Ball ball = new Ball(100);
    Context context;

    public GameView(Context context){
        super(context);
        this.context = context;

        getHolder().addCallback(this);

        balls.add(ball);
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
        for(Ball bal:balls){
            bal.update(balls);
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            canvas.drawColor(Color.WHITE);
            for(Ball bal: balls) {
                bal.draw();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(System.currentTimeMillis() - lastTime > 100) {
            boolean newBall = false;
            for (Ball bal : balls) {
                if (bal.inArea((int) e.getX(), (int) e.getY())) {
                    lastTime = System.currentTimeMillis();
                    bal.hit();
                    newBall = true;

                }
            }
            if (newBall == true) {
                balls.add(new Ball(100));
            }
        }
        return true;
    }

}
