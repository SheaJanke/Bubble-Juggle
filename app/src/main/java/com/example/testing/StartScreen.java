package com.example.testing;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class StartScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    void tick(){

    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        canvas.drawColor(Color.WHITE);
        paint.setTextSize(120);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        paint.setColor(Color.BLUE);
        canvas.drawText("BUBBLE", width/2,(float)(height/5.0),paint);
        canvas.drawText("JUGGLE", width/2,(float)(height/4.0),paint);
    }
}
