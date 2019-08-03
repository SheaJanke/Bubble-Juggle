package com.example.testing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.MotionEvent;

class HighScoreScreen {
    private String[] highScoreKeys = {"1","2","3","4","5"};
    private int[][] numberColors = {{255,255,0,0},{255,255,165,0},{255,255,255,0},{255,0,128,0},{255,0,0,255}};
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context context;

    HighScoreScreen(Context context){
        this.context = context;
    }

    void setHighScore(int highScore){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        for(int a = 0; a < highScoreKeys.length; a ++){
            if(highScore > mPreferences.getInt(highScoreKeys[a],0)){
                for(int b = highScoreKeys.length-1; b > a; b--){
                    mEditor.putInt(highScoreKeys[b],mPreferences.getInt(highScoreKeys[b-1],0));
                    mEditor.commit();
                }
                mEditor.putInt(highScoreKeys[a],highScore);
                mEditor.commit();
                return;
            }
        }
    }

    void tick(){

    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(X(150));
        canvas.drawColor(Color.BLACK);
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        paint.setARGB(255,0,255,255);
        canvas.drawText("HIGHSCORES",X(500), Y(200),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.WHITE);
        canvas.drawText("HIGHSCORES",X(500), Y(200),paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(X(120));
        for(int a = 0; a < highScoreKeys.length; a++){
            setARGB(paint,numberColors[a]);
            canvas.drawRect(X(100), Y(300) + a*Y(250), X(900), Y(520) + a*Y(250), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText((a+1) + ". " + getHighScore(a),X(500), Y(450) + a*Y(250),paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
            canvas.drawRect(X(100), Y(300) + a*Y(250), X(900), Y(520) + a*Y(250), paint);
            canvas.drawText((a+1) + ". " + getHighScore(a),X(500), Y(450) + a*Y(250),paint);
            paint.setStyle(Paint.Style.FILL);
        }
        paint.setARGB(255,0,255,255);
        canvas.drawRect(X(300),Y(1650),X(700), Y(1850), paint);
        paint.setTextSize(X(120));
        paint.setColor(Color.BLACK);
        canvas.drawText("BACK", X(500), Y(1800), paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(X(300),Y(1650),X(700), Y(1850), paint);
        canvas.drawText("BACK", X(500), Y(1800), paint);
    }

    private int getHighScore(int keyIndex){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getInt(highScoreKeys[keyIndex],0);
    }

    private float X(float x){
        return x*width/1000;
    }

    private float Y(float y){
        return y*height/2000;
    }

    private void setARGB(Paint paint, int[]argb){
        paint.setARGB(argb[0],argb[1],argb[2],argb[3]);
    }

    void touched(MotionEvent e, GameView gameView, EndScreen endScreen){
        if(e.getY() > Y(1650) && e.getY() < Y(1850) && e.getX()> X(300) && e.getX() < X(700)){
            gameView.setGameState(2);
            endScreen.reset();
        }
    }
}
