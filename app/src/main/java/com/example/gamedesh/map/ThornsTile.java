package com.example.gamedesh.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.gamedesh.Block;
import com.example.gamedesh.GameDisplay;

public class ThornsTile extends Block {
    private int width;


    public ThornsTile(Rect mapLocation,double positionX,double positionY){
        super(mapLocation,positionX,positionY);
        super.positionX = mapLocation.centerX();
        super.positionY = mapLocation.centerY();
        width = mapLocation.width();
    }


    @Override
    public void update(GameDisplay gameDisplay) {
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        Paint paint = new Paint();
        int color = Color.RED;
        paint.setColor(color);

        mapLocation.set(
                (int) gameDisplay.gameToDisplayCoordination(positionX - width),
                mapLocation.top,
                (int) gameDisplay.gameToDisplayCoordination(positionX + width),
                mapLocation.bottom
        );


        //mapLocation.offset(gameDisplay.gameTODeltaBetweenCoordination(),0);

        canvas.drawRect(mapLocation,paint);
        System.out.println();
    }

    @Override
    public void update() {
    }
}
