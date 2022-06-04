package com.example.gamedesh.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.gamedesh.Block;
import com.example.gamedesh.GameDisplay;

public class RegularBlock extends Block {

    //private int spriteIndex;



    public RegularBlock(Rect mapLocation, double positionX, double positionY){
        super(mapLocation,positionX,positionY);
        //this.spriteIndex = spriteIndex;
    }


    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {

    }

    @Override
    public void update() {}
    @Override
    public void update(GameDisplay gameDisplay) {
    }
}
