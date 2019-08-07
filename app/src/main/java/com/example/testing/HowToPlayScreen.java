package com.example.testing;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class HowToPlayScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    void tick(){

    }
    void render(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(X(130));
        paint.setARGB(255,0,255,255);
        canvas.drawText("HOW TO PLAY", X(500), Y(150), paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawText("HOW TO PLAY", X(500), Y(150), paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(X(65));
        paint.setARGB(255,255,0,0);
        canvas.drawText("1. Keep the bubbles from", X(500), Y(300),paint);
        canvas.drawText("falling off the screen.", X(500), Y(380),paint);
        paint.setARGB(255,255,165,0);
        canvas.drawText("2. Tap the bubbles underneath", X(500), Y(520),paint);
        canvas.drawText("the dotted line to make", X(500), Y(600),paint);
        canvas.drawText("them bounce.", X(500), Y(680),paint);
        paint.setARGB(255,255,255,0);
        canvas.drawText("3. Different color bubbles", X(500), Y(800),paint);
        canvas.drawText("will bounce off of each", X(500), Y(880),paint);
        canvas.drawText("other, while same color", X(500), Y(960),paint);
        canvas.drawText("bubbles will pass through.", X(500), Y(1040),paint);
        paint.setARGB(255,0,128,0);
        canvas.drawText("4. Your score increases", X(500), Y(1180),paint);
        canvas.drawText("faster when more bubbles", X(500), Y(1260),paint);
        canvas.drawText("are on the screen.", X(500), Y(1340),paint);
        paint.setARGB(255,0,0,255);
        canvas.drawText("You have 5 lives!", X(500), Y(1480),paint);
        paint.setARGB(255,0,255,255);
        canvas.drawRect(X(300),Y(1650),X(700), Y(1850), paint);
        paint.setTextSize(X(120));
        paint.setColor(Color.BLACK);
        canvas.drawText("BACK", X(500), Y(1850), paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(X(300),Y(1650),X(700), Y(1850), paint);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("BACK", X(500), Y(1850), paint);


    }
    void touched(MotionEvent e){

    }

    private float X(float x){
        return x*width/1000;
    }

    private float Y(float y){
        return y*height/2000;
    }
}
