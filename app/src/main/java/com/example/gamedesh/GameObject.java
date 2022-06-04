package com.example.gamedesh;

import android.graphics.Canvas;

public abstract class GameObject {

    protected double positionX;
    protected double positionY;

    public GameObject(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void  draw(Canvas canvas,GameDisplay gameDisplay);
    public abstract void  update();


}
