package com.maze.game.objects.gameObjects.standard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.objects.DirectionEnum;
import com.maze.game.objects.gameObjects.GameObject;

import static com.maze.game.objects.utils.PathToFilesUtil.ENEMY;

public class Enemy implements GameObject {

    private int x;
    private int y;
    @Override
    public void addToMap() {

    }

    @Override
    public void render(SpriteBatch batch, int i, int j) {
        batch.begin();
        batch.draw(new Texture(ENEMY), i*20, j*20,20,20);
        batch.end();
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
}
