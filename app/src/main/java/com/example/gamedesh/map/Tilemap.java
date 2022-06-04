package com.example.gamedesh.map;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.gamedesh.Block;
import com.example.gamedesh.Game;
import com.example.gamedesh.GameDisplay;
import com.example.gamedesh.GameObject;

import java.util.ArrayList;
import java.util.logging.ErrorManager;


public class Tilemap {

    private int TILE_WIDTH_PIXELS = 64;
    private int TILE_HEIGHT_PIXELS = 64;



    private int[][] layout;
    //2private Tile[][] tilemap;
    private ArrayList blocks;
    private Bitmap mapBitmap;
    public double playerStartPositionX = 0;
    public double playerStartPositionY = 0;
    public Rect finishBlockArray;
    private Game game;

    public Tilemap(Game game, int[][] maplayout,int width_Pix,int height_Pix) {
        TILE_WIDTH_PIXELS = width_Pix;
        TILE_HEIGHT_PIXELS = TILE_WIDTH_PIXELS;
        this.game = game;
        this.layout = maplayout;
        InitializeTile();
    }

    private void InitializeTile() {


        blocks = new ArrayList();
        for (int y = 0; y < layout.length; y++) {
            for (int x = 0; x < layout[y].length; x++) {
                if(layout[y][x] == 3) {
                    Rect r = getRectByIndex(x, y);
                    finishBlockArray = r;
                    x += 1;
                }
                if(layout[y][x] == 4){
                    Rect r = getRectByIndex(x, y);
                    playerStartPositionX = r.centerX();
                    playerStartPositionY = r.centerY();
                    x+=1;
                }

                if (layout[y][x] != 2)
                    blocks.add(Tile.getBlock(layout[y][x], getRectByIndex(x,y)));
                System.out.println();
            }
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    private Rect getRectByIndex(int x, int y) {
        Rect rect = new Rect(
                x * TILE_WIDTH_PIXELS,
                y * TILE_HEIGHT_PIXELS,
                (x+1) * TILE_WIDTH_PIXELS,
                (y + 1) * TILE_HEIGHT_PIXELS
        );
        return rect;
        //System.out.println();
    }
}