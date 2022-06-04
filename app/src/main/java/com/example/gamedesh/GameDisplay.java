package com.example.gamedesh;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.ContactsContract;

public class GameDisplay {
    //public final Rect DISPLAY_RECT;
    private double OffsiteCordinatsX;
    private double XConst = 397;
    private double displayCentreX;
    private double gameCenterX;
    private double gameCenterY;
    //private double wightPixels;
    //private double heightPixels;
    private GameObject target;

    public GameDisplay(int wightPixels,GameObject target){
        this.target = target;
        //DISPLAY_RECT = new Rect(0,0,wightPixels,heightPixelsY);
        this.displayCentreX = wightPixels/2.0;
        gameCenterY = target.positionY;
        //XConst = ((int) displayCentreX -  (int) gameCenterX);
    }

    public void update(){
        gameCenterX = target.positionX;

        OffsiteCordinatsX = displayCentreX - gameCenterX;
    }

    public double gameToDisplayCoordination(double x){
        System.out.println();
        return x + OffsiteCordinatsX;
    }



/*
    public Rect getGameRect(int wightPixels,int heightPixels) {
        return new Rect(
                (int)(gameCenterX - wightPixels/2),
                (int)(gameCenterY - heightPixels/2),
                (int)(gameCenterX + wightPixels/2),
                (int)(gameCenterY + heightPixels/2)
        );
    }

    public Rect getDisplayRect() {
        return DISPLAY_RECT;
    }


 */

}
