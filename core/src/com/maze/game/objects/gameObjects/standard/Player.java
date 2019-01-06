package com.maze.game.objects.gameObjects.standard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.objects.DirectionEnum;
import com.maze.game.objects.gameObjects.GameObject;

import static com.maze.game.objects.utils.PathToFilesUtil.PLAYER;

public class Player implements GameObject {
    //   private Texture playerText = new Texture(PLAYER);
    private int x,y;

    private int keyCounter;

    @Override
    public void addToMap() {

    }

    @Override
    public void render(SpriteBatch batch,int i,int j) {
        batch.begin();
        batch.draw(new Texture(PLAYER), i*20, j*20,20,20);
        batch.end();
    }

    public void init(){
        x=0;
        y=0;
        keyCounter =0;
    }

    public void moveTo(DirectionEnum directionEnum){
        switch (directionEnum) {
            case gora:
                if(y!=29){ y++;break;}
            case dol:
                if(y!=0){ y--;break;}
            case lewo:
                if(x!=0){ x--;break;}
            case prawo:
                if(x!=29){ x++;break;}
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getKeyCounter() {
        return keyCounter;
    }

    public void setKeyCounter(int keyCounter) {
        this.keyCounter = keyCounter;
    }
}
