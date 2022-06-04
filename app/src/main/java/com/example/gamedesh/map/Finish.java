package com.example.gamedesh.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.gamedesh.Block;
import com.example.gamedesh.Game;
import com.example.gamedesh.GameDisplay;
import com.example.gamedesh.GameLoop;
import com.example.gamedesh.GameObject;
import com.example.gamedesh.Player;

public class Finish {

    private double positionX;
    private double positionY;

    private Player player;
    private int wight;
    private int height;

    public Finish(double positionX, double positionY, Player player,int wight,int height){

        this.positionX = positionX;
        this.positionY = positionY;

        this.player = player;
        this.wight = wight;
        this.height = height;
    }




    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        Paint paint = new Paint();
        int color = Color.YELLOW;
        paint.setColor(color);
        canvas.drawRect( (int) gameDisplay.gameToDisplayCoordination(positionX) - wight/2,
                (int)    positionY - height/2,
                (int)    gameDisplay.gameToDisplayCoordination(positionX) + wight/2,
                (int)    positionY + height/2,paint);
    }


    public void update(Game game) {

        double distant = Math.sqrt( Math.pow(positionX - player.getPositionX(),2) + Math.pow(positionY - player.getPositionY(),2) );
        if(distant < wight/2){
            game.GameOver(true);
        }
    }

}
