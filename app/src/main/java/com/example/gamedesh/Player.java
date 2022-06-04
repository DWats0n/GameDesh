package com.example.gamedesh;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class Player extends GameObject {

    private double speed = 0;
    private double gravity = 7;
    private double jumpHeat = 0;
    private double velocity;
    public boolean isGrounded = false;

    private Jostik jostik;
    private  Bottom bottom;


    private double radius;

    private Paint paint;
    double boost = 1;
    private boolean r = true;
    private boolean l = true;


    public Player(double positionX, double positionY , double radius, Jostik jostik, Bottom bottom){
        super(positionX,positionY);
        this.jostik = jostik;
        this.bottom = bottom;
        this.radius = radius;
        speed = radius/3;
        jumpHeat = -1 * radius*2;
        paint = new Paint();
        int color = Color.argb(255,255,255,255);
        paint.setColor(color);
    }


    public void draw(Canvas canvas,GameDisplay gameDisplay){
        canvas.drawCircle((int)gameDisplay.gameToDisplayCoordination(positionX), (float) (positionY), (float) radius,paint);
    }
    public  void update() {



        double vr = jostik.getValue() > 0? speed * jostik.getValue():0;
        double vl = jostik.getValue() < 0? speed * -1 *Math.abs(jostik.getValue()):0;

        if(!r)
            vr = 0;
        if(!l)
            vl = 0;



        if(velocity > 7)
            velocity = 30;

        if (isGrounded) {
            velocity = 0;
            boost = 1;
        } else {
            velocity += gravity;
        }

        if (bottom.getIsPressed() == 1 && isGrounded) {
            velocity += jumpHeat;
            boost = 1.7;
        }


        positionY += velocity;
        positionX += (vr * boost + vl * boost);
    }



    public void CanMoveR(boolean v){r = v;}
    public void CanMoveL(boolean v){l = v;}
    public void velocityIsZero(){velocity = 0;}






    public double getRadius(){
        return radius;
    }

    public void setIsGrounded(boolean v,double y){
        isGrounded = v;
        positionY = y;
    }

    public double getPositionX(){return positionX;}
    public double getPositionY(){return positionY;}
}
