package com.example.gamedesh;


import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;
import static com.example.gamedesh.LevelChousse.FILE_NAME;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class GameOver{

    private long timer;
    private Game game;
    private int Level;

    private double widthPixels;
    private double heightPixels;

    public double RestartButtomX;
    public double RestartButtomY;

    public double ExitButtomX;
    public double ExitButtomY;

    private LevelChousse context;

    public GameOver(Game game,double widthPixels, double heightPixels,LevelChousse context,int Level){
        this.context = context;
        this.Level = Level;
        this.game = game;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;

        RestartButtomX = widthPixels/2 - widthPixels/4;
        RestartButtomY = heightPixels/2 + heightPixels/4;

        ExitButtomX = widthPixels/2 + widthPixels/4;
        ExitButtomY = heightPixels/2 + heightPixels/4;

    }

    public void draw(Canvas canvas){



        String message = "Время прохождение: " + time(timer);

        Paint paint = new Paint();
        int color = Color.WHITE;
        paint.setColor(color);
        paint.setTextSize((int) widthPixels/30);

        canvas.drawText(message,(float) (widthPixels/2 - widthPixels/4),(float) (heightPixels/2 - heightPixels/4),paint);

        drawRestart(canvas);
        drawExit(canvas);


    }

    private void drawRestart(Canvas canvas){
        String message = "Restart";

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        Rect rect = new Rect(
                (int) (RestartButtomX - widthPixels/10),
                (int) (RestartButtomY - heightPixels/10),
                (int) (RestartButtomX + widthPixels/10),
                (int) (RestartButtomY + heightPixels/10));
        canvas.drawRect(rect,paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize((int) rect.height()/2);
        canvas.drawText(message,rect.left + rect.width()/10,rect.bottom - rect.height()/4,paint);

    }
    private void drawExit(Canvas canvas){
        String message = "Exit";

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Rect rect = new Rect(
                (int) (ExitButtomX - widthPixels/10),
                (int) (ExitButtomY - heightPixels/10),
                (int) (ExitButtomX + widthPixels/10),
                (int) (ExitButtomY + heightPixels/10));
        canvas.drawRect(rect,paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize((int) rect.height()/2);
        canvas.drawText(message,rect.left + rect.width()/10,rect.bottom - rect.height()/4,paint);

    }


    private String time(long timer){
        long time = timer;
        long temp;

        temp = time % 1000;

        String milisecond = (temp < 100 ? "0":"") + (temp < 10 ? "0":"") + temp;
        time = (int) time / 1000;

        temp = time % 60;
        String second = (temp < 10 ? "0":"") + temp;
        time = (int) time / 60;

        temp = time % 100;
        String minute = (temp < 10 ? "0":"") + temp;

        return minute +" : "+second+" : "+milisecond;
    }

    public void update(){

    }

    public void PressRestart(double touchX,double touchY){
        if( touchX > RestartButtomX - widthPixels/10 && touchX < RestartButtomX + widthPixels/10 &&
        touchY > RestartButtomY - heightPixels/10 && touchY < RestartButtomY + heightPixels/10){
            Player player = game.getPlayer();
            player.positionX = game.playerStartPositionX;
            player.positionY = game.playerStartPositionY;
            player.velocityIsZero();
            game.timeZero();
            game.GameOver(false);
        }
    }
    public void PressExit(double touchX,double touchY){
        if( touchX > ExitButtomX - widthPixels/10 && touchX < ExitButtomX + widthPixels/10 &&
                touchY > ExitButtomY - heightPixels/10 && touchY < ExitButtomY + heightPixels/10)
        {
            try {
                Intent intent = new Intent(context,LevelChousse.class);
                SaveResult();
                game.gl.stopGame();
                context.startActivity(intent);

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void SaveResult(){

        FileOutputStream file = null;

        try {
            int o = 0001;
            String[] strings = text(2);
            String t = strings[Level];
            String tim = time(timer);
            if(!t.equals("")){
                for(int i = 0; i < t.split(" : ").length-1;i++){

                    if(!t.split(" : ")[i].equals(tim.split(" : ")[i])){
                        if( Integer.parseInt(t.split(" : ")[i]) > Integer.parseInt(tim.split(" : ")[i])){
                            strings[Level] = tim;
                            break;
                        }

                    }

                }
            }
            else {
                strings[Level] = tim;
            }


            String text = "";
            int i = 0;
            for(String s : strings){

                text += "\n."+i+s;
                i++;
            }

            file = context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            file.write(text.getBytes());
            file.close();





        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String[] text(int MaxLevel){
        MaxLevel +=1;
        String message[] = new String[MaxLevel];

        for(int i = 0; i < message.length; i++){
            message[i] = "";
        }

        FileInputStream file = null;

        try {
            file = context.openFileInput(FILE_NAME);
            byte[] bytes = new byte[file.available()];
            file.read(bytes);
            String text = new String (bytes);

            int i = 0;
            String[] tt = text.split("\n\\.");
            System.out.println();
            for(String s : tt){


                String n = "";

                try {
                    n = String.valueOf(s.charAt(0));
                }catch (Exception e){
                    n = "";
                }

                if(n.equals(String.valueOf(i))){
                    message[i] = s.substring(1);
                    i++;
                }

                System.out.println();

            }


        } catch (IOException e) {

        }

        System.out.println(message);
        return message;
    }





    public void setTimer(long t){
        timer = t;
    }

}
