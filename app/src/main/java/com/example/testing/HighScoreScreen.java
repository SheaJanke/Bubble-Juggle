package com.example.testing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;

public class HighScoreScreen {
    private String[] highScoreKeys = {"1","2","3","4","5"};
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
        paint.setTextSize((float)width * 100/1080);
        paint.setColor(Color.BLACK);
        canvas.drawColor(Color.WHITE);
        for(int a = 0; a < highScoreKeys.length; a++){
            canvas.drawText((a+1) + ". " + getHighScore(a),width/2f, 100f*a*2160/height + 500,paint);
        }
    }

    int getHighScore(int keyIndex){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getInt(highScoreKeys[keyIndex],0);
    }
}
