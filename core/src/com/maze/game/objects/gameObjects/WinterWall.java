package com.maze.game.objects.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.maze.game.objects.PathToFilesUtil.WINTER_WALL;

public class WinterWall implements GameObject{
    private Texture wallTexture = new Texture(WINTER_WALL);
    @Override
    public void addToMap() {

    }

    @Override
    public void render(SpriteBatch batch, int i, int j) {
        batch.begin();
        batch.draw(wallTexture, i*20, j*20,20,20);
        batch.end();
    }
}
