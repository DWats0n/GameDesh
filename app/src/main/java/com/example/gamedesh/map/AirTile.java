package com.example.gamedesh.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.gamedesh.Block;
import com.example.gamedesh.GameDisplay;

public class AirTile extends Block {
    public AirTile(Rect mapLocation,double positionX,double positionY){
        super(mapLocation,positionX,positionY);
    }

    @Override
    public void update(GameDisplay gameDisplay) {
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        Paint paint = new Paint();
        int color = Color.GREEN;
        paint.setColor(color);
        canvas.drawRect(mapLocation,paint);
    }

    @Override
    public void update() {
    }
}
