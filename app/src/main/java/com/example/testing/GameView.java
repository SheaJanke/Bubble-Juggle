package com.example.testing;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private HowToPlayScreen howToPlayScreen;
    private int gameState = 0;
    Context context;
    Bitmap redX = BitmapFactory.decodeResource(getResources(), R.drawable.redx);
    Bitmap greyX = BitmapFactory.decodeResource(getResources(), R.drawable.greyx);

    public GameView(Context context){
        super(context);
        this.context = context;

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        startScreen = new StartScreen();
        mainGame = new MainGame(redX,greyX);
        endScreen = new EndScreen(context);
        highScoreScreen = new HighScoreScreen(context);
        howToPlayScreen = new HowToPlayScreen();

        setFocusable(true);
        startScreen.reset();
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
                gameState = 2;
                highScoreScreen.setHighScore(mainGame.getScore());
                endScreen.reset();
            }
        }else if(gameState == 2){
            endScreen.tick();
        }else if(gameState == 3){
            highScoreScreen.tick();
        }else if(gameState == 4){
            howToPlayScreen.tick();
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
            }else if(gameState ==4){
                howToPlayScreen.render(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(gameState == 0){
            startScreen.touched(e, this, mainGame,highScoreScreen);
        }else if(gameState == 1) {
            mainGame.touched(e);
        }else if(gameState == 2){
            endScreen.touched(e,this, mainGame,startScreen,highScoreScreen);
        }else if(gameState == 3){
            highScoreScreen.touched(e,this,startScreen);
        }else if(gameState == 4){
            howToPlayScreen.touched(e,this,startScreen);
        }
        return true;
    }

    void setGameState(int gameState){
        this.gameState = gameState;
    }

}
