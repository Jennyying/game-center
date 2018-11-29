package com.example.lenovo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class StateModel {
    //life
    private Bitmap life;
    private int numLife = 2;
    private int score = 0;
    //score
    private Paint paintScore;
    //background
    private Bitmap background;

    public void setLife(Bitmap life) {
        this.life = life;
    }

    public int getScore() {
        return score;
    }

    public int getNumLife() {
        return numLife;
    }

    public Paint getPaintScore() {
        return paintScore;
    }

    public void setBackground(Bitmap background) {
        this.background = background;
    }

    public Bitmap getBackground() {
        return background;
    }

    public void changeScore(){
        this.score ++;
    }
    public void changeLife(){
        this.numLife ++;
    }

    public void generatePaint(){
        paintScore.setColor(Color.GREEN);
        paintScore.setTextSize(32);
        paintScore.setTypeface(Typeface.DEFAULT);
        paintScore.setAntiAlias(true);
    }
    public void drawLife(Canvas canvas){
        for (int i = 0; i < numLife; i++) {
            int x = (int) (560 + life.getWidth() * 1.5 * i);
            int y = 30;
            canvas.drawBitmap(life, x, y, null);
        }
    }
}
