package com.example.gamedesh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.gamedesh.map.Finish;
import com.example.gamedesh.map.Tilemap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Random;

import javax.security.auth.Destroyable;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final int BlockInWight = 50;
    private final int BlockInHeight = 35;

    private boolean GameIsOver = false;
    public double time = 0;

    private final Player player;

    public double playerStartPositionX;
    public double playerStartPositionY;

    private final Bottom bJump;
    private final RestartButton restartButton;
    private final Jostik jostik;
    private final Tilemap tilemap;
    private final GameOver gameOver;
    private Finish finishBlock;
    private final ArrayList<Block> blocks;
    public GameLoop gl;
    private Context context;
    private LevelChousse lc;

    public long Time = 0;

    int i = 0;



    private final GameDisplay gameDisplay;

    public Game(Context context,int[][] mapLayout,LevelChousse lc,int level) {
        super(context);
        this.lc = lc;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        SurfaceHolder sh = getHolder();
        sh.addCallback(this);



        gl = new GameLoop(this, sh);



        bJump = new Bottom(displayMetrics.widthPixels-300,displayMetrics.heightPixels/2+displayMetrics.heightPixels/4,BlockInHeight*5);

        restartButton = new RestartButton(displayMetrics.widthPixels - 100,100,50,this);



        jostik = new Jostik(displayMetrics.widthPixels/2 - displayMetrics.widthPixels/4,
                displayMetrics.heightPixels/2+displayMetrics.heightPixels/4, 10 * BlockInWight,BlockInHeight * 5 );



        tilemap = new Tilemap(this,mapLayout,displayMetrics.widthPixels/BlockInWight,displayMetrics.heightPixels/BlockInHeight);

        playerStartPositionX = tilemap.playerStartPositionX;
        playerStartPositionY = tilemap.playerStartPositionY;


        player = new Player(500, 500,displayMetrics.widthPixels/BlockInWight/2,jostik,bJump);

        player.positionX = tilemap.playerStartPositionX;

        player.positionY = tilemap.playerStartPositionY-100;
        player.velocityIsZero();


        finishBlock = new Finish(tilemap.finishBlockArray.centerX(),tilemap.finishBlockArray.centerY(),player,
                tilemap.finishBlockArray.width(),tilemap.finishBlockArray.height());



        gameDisplay = new GameDisplay(displayMetrics.widthPixels,player);

        gameOver = new GameOver(this,displayMetrics.widthPixels,displayMetrics.heightPixels,lc,level);




        blocks = tilemap.getBlocks();


        setFocusable(true);
    }



    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        //System.out.println(jostik.getTouchIndex()+"    "+ bJump.getTouchIndex()+"    "+motionEvent.getActionMasked());
            switch (motionEvent.getActionMasked())
            {
                case MotionEvent.ACTION_DOWN:
                    if(GameIsOver == false) {
                        if (bJump.isPressed(motionEvent.getX(), motionEvent.getY())) {
                            bJump.setTouchIndex(motionEvent.getActionIndex());
                            bJump.setIsPressed(true);
                        }

                        if (jostik.isPressed(motionEvent.getX(), motionEvent.getY())) {
                            jostik.setTouchIndex(motionEvent.getActionIndex());
                            jostik.setIsPressed(true);
                        }
                        restartButton.isPress(motionEvent.getX(),motionEvent.getY());
                    }
                    else {
                        System.out.println("yes");
                        gameOver.PressRestart(motionEvent.getX(),motionEvent.getY());
                        gameOver.PressExit(motionEvent.getX(),motionEvent.getY());
                    }
                    return true;

                case MotionEvent.ACTION_POINTER_DOWN:


                    if (bJump.isPressed(motionEvent.getX(motionEvent.getActionIndex()), motionEvent.getY(motionEvent.getActionIndex()))) {
                        bJump.setIsPressed(true);
                    }

                    if(jostik.isPressed(motionEvent.getX(motionEvent.getActionIndex()),motionEvent.getY(motionEvent.getActionIndex()))){
                        jostik.setTouchIndex(motionEvent.getActionIndex());
                        jostik.setIsPressed(true);
                    }
                    return true;

                case MotionEvent.ACTION_MOVE:
                    try {
                        if (jostik.getIsPressed()) {
                            jostik.stikMove(motionEvent.getX(jostik.getTouchIndex()));
                        }
                    }catch (Exception e){}
                //case MotionEvent.

                    return true;
                case MotionEvent.ACTION_UP:
                    bJump.setIsPressed(false);
                    jostik.setIsPressed(false);
                    jostik.stikMove();
                    return true;

                case MotionEvent.ACTION_POINTER_UP:
                    bJump.setIsPressed(false);
                    if(jostik.getTouchIndex() != 0)
                    jostik.setTouchIndex(jostik.getTouchIndex()-1);
            }

        return super.onTouchEvent(motionEvent);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        //Log.d("Game.java","surfaceCreated()");
        gl.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        if(GameIsOver == false) {
            for (Block o : tilemap.getBlocks()) {
                o.draw(canvas, gameDisplay);
            }

            finishBlock.draw(canvas, gameDisplay);

            player.draw(canvas, gameDisplay);


            jostik.draw(canvas);

            bJump.draw(canvas);

            restartButton.draw(canvas);

            drawTime(canvas);
        }
        else {
            gameOver.draw(canvas);
        }

    }





    public void drawTime(Canvas canvas){

        long time = gl.getTime();
        gameOver.setTimer(time);
        long temp;

        temp = time % 1000;

        String milisecond = (temp < 100 ? "0":"") + (temp < 10 ? "0":"") + temp;
        time = (int) time / 1000;



        temp = time % 60;
        String second = (temp < 10 ? "0":"") + temp;
        time = (int) time / 60;

        temp = time % 100;
        String minute = (temp < 10 ? "0":"") + temp;



        //String message = String.valueOf(gl.getTime());
        String message = minute +" : "+second+" : "+milisecond;
        Paint paint = new Paint();
        int color = Color.WHITE;
        paint.setColor(color);
        paint.setTextSize(100);
        canvas.drawText(message,300,200,paint);



    }


    public void update(){

        if(GameIsOver == false) {
            player.update();
            gameDisplay.update();
            finishBlock.update(this);


            isColliding(blocks, player);


            if (player.positionY > 1700) {
                player.positionY = tilemap.playerStartPositionY;
                player.positionX = tilemap.playerStartPositionX;
                player.velocityIsZero();
                timeZero();
            }
        }
        else {
            gameOver.update();
        }



    }

    public static void isColliding(ArrayList<Block> iteratorBlock,Player player){

        ArrayList<Block> CoordinateY = new ArrayList();
        ArrayList<Block> CoordinateX = new ArrayList();

        for (Block block : iteratorBlock){

            if((player.positionX > block.positionX - block.mapLocation.width()/2 &&
                player.positionX < block.positionX + block.mapLocation.width()/2)){

                if((player.positionY + player.getRadius() >= block.mapLocation.top && block.positionY - player.positionY >= 0 ) ||
                        (player.positionY - player.getRadius() <= block.mapLocation.bottom && block.positionY - player.positionY <= 0 ) )
                CoordinateY.add(block);
            }

            if( block.positionY > player.positionY - player.getRadius() &&
                    block.positionY < player.positionY + player.getRadius() &&
                    (Math.abs(player.positionX - block.positionX) <= player.getRadius()+block.mapLocation.width()/2) )
            {
                CoordinateX.add(block);
            }


        }

        if(CoordinateY.size() == 0)
            player.setIsGrounded(false,player.positionY);
        else
        for ( Block o : CoordinateY ){
            if(player.positionY - o.positionY < 0 )
                player.setIsGrounded(true,o.mapLocation.top - player.getRadius());
            else{
                player.setIsGrounded(false, o.mapLocation.bottom + player.getRadius());
                player.velocityIsZero();
            }
        }





        if(CoordinateX.size() == 0) {
            player.CanMoveR(true);
            player.CanMoveL(true);
        }
        else{
            for (Block o: CoordinateX){
                if(player.positionX - o.positionX > 0){
                    player.CanMoveL(false);
                }
                else{
                    player.CanMoveR(false);
                }
            }
        }

        System.out.println();

    }
    public void GameOver(boolean v){
        GameIsOver = v;
    }
    public Player getPlayer(){return player;}

    public void timeZero(){
        gl.setStartTimer();
    }

}

class RestartButton{

    private double positionX;
    private double positionY;
    private double radius;
    private Game game;

    public RestartButton(double positionX,double positionY,double radius,Game game){
        this.game = game;
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
    }

    public void isPress(double touchX,double touchY){
        if(((touchX < positionX +radius) && (touchX > positionX -radius))
                && ((touchY < positionY + radius) && (touchY > positionY -radius))){
            game.getPlayer().positionX = game.playerStartPositionX;
            game.getPlayer().positionY = game.playerStartPositionY;
            game.getPlayer().velocityIsZero();
            game.timeZero();
        }
    }
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        int color = Color.argb(100,100,100,100);
        paint.setColor(color);
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius,paint);
    }

}


