package com.example.testing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.LinkedList;

public class EndScreen {
   private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
   private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
   private SharedPreferences mPreferences;
   private SharedPreferences.Editor mEditor;
   private Context context;

   EndScreen(Context context){
       this.context = context;
   }

   private LinkedList<Ball> balls = new LinkedList<>();
   private Ball playBall = new Ball(width/6, (float)width/2, (float)height/2,5,0);
   void tick(){
        for(Ball ball: balls){
            ball.calculate(balls);
            if(ball.getY() > height-(float)ball.getRadius()/2){
                ball.setNewVelY(-ball.getVelY());
            }
        }
        for(Ball ball: balls){
            ball.updateVel();
        }
   }
   void render(Canvas canvas, int score){
       canvas.drawColor(Color.WHITE);
       Paint paint = new Paint();
       paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
       paint.setTextAlign(Paint.Align.CENTER);
       paint.setTextSize((float)(150/1080.0) * width);
       paint.setColor(Color.RED);
       canvas.drawText("GAME OVER", (float)width/2, (float)height/6, paint);
       paint.setTextSize((float)(100/1080.0) * width);
       paint.setColor(Color.BLACK);
       canvas.drawText("SCORE = " + score, (float)width/2, (float)height/4, paint);
       canvas.drawText("HIGHSCORE = " + getHighScore(), (float)width/2, (float)height/3, paint);
       for(Ball ball:balls){
           ball.draw(canvas);
       }
       canvas.drawText("PLAY", balls.get(0).getX(),balls.get(0).getY() + (float)height/50,paint);

   }

   void reset(){
       balls.clear();
       balls.add(playBall);
   }


   void touched(MotionEvent e, GameView gameView, MainGame mainGame){
       if(balls.get(0).inArea((int)e.getX(),(int)e.getY())){
           setHighScore(mainGame.getScore());
           mainGame.reset();
           gameView.setGameState(1);
       }
   }

   void setHighScore(int highScore){
       mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
       mEditor = mPreferences.edit();
       mEditor.putInt("HighScore", highScore);
       mEditor.commit();


   }

   int getHighScore(){
       mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
       mEditor = mPreferences.edit();
       return mPreferences.getInt("HighScore", 0);
   }

}
