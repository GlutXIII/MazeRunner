package com.maze.game.objects.gameObjects.standard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.objects.gameObjects.Abstract.AbstractWall;
import com.maze.game.objects.gameObjects.GameObject;

import static com.maze.game.objects.utils.PathToFilesUtil.WALL;

public class Wall implements GameObject, AbstractWall {
    private Texture wallTexture = new Texture(WALL);
    @Override
    public void addToMap() {

    }

    @Override
    public void render(SpriteBatch batch,int i,int j) {
        batch.begin();
        batch.draw(wallTexture, i*20, j*20,20,20);
        batch.end();
    }
}
