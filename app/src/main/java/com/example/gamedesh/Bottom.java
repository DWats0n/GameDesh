package com.example.gamedesh;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bottom {

    private double positionX;
    private double positionY;
    private double radius;
    private Paint paint;
    private boolean isPressed = false;
    private int maining;
    private int touchIndex;





    public Bottom(double positionX, double positionY,double radius){
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;

        paint = new Paint();
        int color = Color.argb(63, 255,0,0);
        paint.setColor(color);

    }

    public void draw(Canvas canvas){
        canvas.drawCircle((float) positionX,(float)positionY,(float)radius,paint);
    }

    public void setTouchIndex(int index){
        touchIndex = index;
    }
    public int getTouchIndex(){
        return touchIndex;
    }


    public boolean isPressed(double TouchPositionX,double TouchPositionY){
        boolean ans = false;

        if(((TouchPositionX < positionX +radius) && (TouchPositionX > positionX -radius))
                && ((TouchPositionY < positionY + radius) && (TouchPositionY > positionY -radius))){

            ans = true;
        }
        return ans;
    }
    public void setIsPressed(boolean isPressed){
        this.isPressed = isPressed;

    }

    public int getIsPressed(){
        return isPressed ? 1:0;
    }

}
