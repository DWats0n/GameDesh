package com.example.gamedesh;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Vector;

public class Jostik {

    private double radius;
    private boolean isPressed = false;
    private int touchIndex = -1;

    private Paint paint;
    private Paint paint1;

    private double PositionX;
    private double PositionY;
    private double stikPositionX;
    private double stikPositionY;

    private double a;
    private double b;

    public Jostik (double PositionX,double PositionY,double a,double b){
        this.PositionX = PositionX;
        this.PositionY = PositionY;

        stikPositionX = PositionX;
        stikPositionY = PositionY;
        this.radius = b/2;

        this.a = a;
        this.b = b;


    }

    public void draw(Canvas canvas){
        paint = new Paint();
        int color = Color.argb(100,255,100,100);
        paint.setColor(color);
        canvas.drawRect( (float) (PositionX - a/2),
                (float) (PositionY - b/2),
                (float) (PositionX + a/2),
                (float) (PositionY + b/2)
                , paint);
        paint1 = new Paint();
        paint1.setColor(Color.BLUE);
        canvas.drawCircle((float)stikPositionX,(float)stikPositionY,(float)radius,paint1);
    }
    public void setTouchIndex(int index){
        touchIndex = index;
    }
    public int getTouchIndex(){
        return touchIndex;
    }

    public double getValue(){

        // Vector vector = new Vector(Vector.Distant((a/2), stikPositionX));
        // float distant = stikPositionX - (a/2);
        // float vectorNoralizete = Math.pow((a/2)/distant),2) + Math.pow(stikPositionX/distant,2)
        double distant = a/2;
        double value =  (stikPositionX- PositionX)/distant;
        return value;
    }

    public boolean isPressed(double TouchPositionX,double TouchPositionY){
        boolean ans = false;

        if(((TouchPositionX < PositionX +radius) && (TouchPositionX > PositionX -radius))
                && ((TouchPositionY < PositionY + radius) && (TouchPositionY > PositionY -radius))){

            ans = true;
        }
        return ans;
    }
    public void stikMove(double touchPosition){
        double X = touchPosition;
        if(X > PositionX + a/2){
            X = PositionX + a/2;
        }
        if(X < PositionX - a/2){
            X = PositionX - a/2;
        }
        stikPositionX = X;
    }


    public void stikMove(){
        stikPositionX = PositionX;
    }


    public void setIsPressed(boolean isPressed){
        this.isPressed = isPressed;
    }

    public boolean getIsPressed(){
        return isPressed;
    }
}
