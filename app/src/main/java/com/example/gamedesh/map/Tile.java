package com.example.gamedesh.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.gamedesh.Block;
import com.example.gamedesh.GameObject;

import java.util.ArrayList;

abstract class Tile {

    protected final Rect mapLocation;

    public Tile(Rect mapLocation){
        this.mapLocation = mapLocation;
    }

    public enum TileType{
        GROUND_TILE,
        THORNS_TILE,
        AIR_TILE,
    }


    public static Block getBlock(int idxTileType, Rect mapLocation){
        Block object = null;
        switch (TileType.values()[idxTileType]){

            case GROUND_TILE:
                object = new GroundTile(mapLocation,0,0);
                return object;

            case THORNS_TILE:
                object = new ThornsTile(mapLocation,0,0);
                return object;

            case AIR_TILE:
                object = new AirTile(mapLocation,0,0);
                return object;
            default:
                return null;
        }
    }

    public abstract void draw(Canvas canvas);

}
