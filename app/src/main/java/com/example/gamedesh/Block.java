package com.example.gamedesh;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Spliterators;

public abstract class Block extends GameObject  {

    protected Rect mapLocation;

    public Block(Rect mapLocation,double positionX,double positionY){
        super(positionX,positionY);
        this.mapLocation = mapLocation;
    }

    public abstract void  update(GameDisplay gameDisplay);




    public static double getDistance(GameObject one, GameObject two){
        return 0;
    }

}
