package com.example.testing;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.view.MotionEvent;

public class EndScreen {
   private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
   private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
   void tick(){

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

   }

   void touched(MotionEvent e){}

}
