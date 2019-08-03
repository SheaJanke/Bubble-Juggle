package com.example.testing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;



public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    private StartScreen startScreen;
    private MainGame mainGame;
    private EndScreen endScreen;
    private HighScoreScreen highScoreScreen;
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int gameState = 2;
    Context context;

    public GameView(Context context){
        super(context);
        this.context = context;

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        startScreen = new StartScreen();
        mainGame = new MainGame();
        endScreen = new EndScreen(context);
        highScoreScreen = new HighScoreScreen(context);

        setFocusable(true);
        //startScreen.reset();
        endScreen.reset();
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
        if(gameState == 0){
            startScreen.tick();
        }else if(gameState == 1) {
            mainGame.tick();
            if(!mainGame.isAlive()){
                gameState = 3;
                highScoreScreen.setHighScore(mainGame.getScore());
                endScreen.reset();
            }
        }else if(gameState == 2){
            endScreen.tick();
        }else if(gameState == 3){
            highScoreScreen.tick();
        }
    }


    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            if(gameState == 0){
                startScreen.render(canvas);
            }else if(gameState == 1){
               mainGame.render(canvas);
            }else if(gameState == 2){
                endScreen.render(canvas,mainGame.getScore());
            }else if(gameState == 3){
                highScoreScreen.render(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(gameState == 0){
            startScreen.touched(e, this, mainGame);
        }else if(gameState == 1) {
            mainGame.touched(e);
        }else if(gameState == 2){
            endScreen.touched(e,this, mainGame);
        }else if(gameState == 3){
            highScoreScreen.touched(e,this,endScreen);
        }
        return true;
    }

    void setGameState(int gameState){
        this.gameState = gameState;
    }

}
