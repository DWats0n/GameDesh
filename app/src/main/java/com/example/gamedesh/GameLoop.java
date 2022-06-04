package com.example.gamedesh;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.gamedesh.Game;

public class GameLoop extends Thread{

    private static final double MAX_UPS = 30;
    private static final double UPS_PERIOD = 1E+3/ MAX_UPS;
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private GameOver gameOver;
    public long StartTimer = System.currentTimeMillis();



    public GameLoop(Game game, SurfaceHolder sh){
        this.surfaceHolder = sh;
        this.game = game;
    }



    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run(){
        super.run();


        int updateCount = 0;
        int fraimCount = 0;

        long startTime;
        long elapswdTime;
        long sleepTime;

        Canvas canvas = null;
        startTime = System.currentTimeMillis();

        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }

            } catch (IllegalAccessError e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        fraimCount++;
                    }
                    catch (IllegalAccessError e){
                        e.printStackTrace();
                    }
                }
            }



            elapswdTime = System.currentTimeMillis() - startTime;




            sleepTime = (long) (fraimCount*UPS_PERIOD - elapswdTime);
            if(sleepTime > 0){
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (sleepTime < 0 && updateCount < MAX_UPS - 1){
                game.update();
                updateCount++;
                elapswdTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (fraimCount*UPS_PERIOD - elapswdTime);
            }

            elapswdTime = System.currentTimeMillis() - startTime;
            if (elapswdTime >= 1000) {
                fraimCount = 0;
                updateCount = 0;
                startTime = System.currentTimeMillis();
            }





        }

    }

    public long getTime(){return System.currentTimeMillis() - StartTimer;}
    public void setStartTimer(){StartTimer = System.currentTimeMillis();}
    public void stopGame(){isRunning = false;}
}
