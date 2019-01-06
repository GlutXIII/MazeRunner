package com.maze.game.objects.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.maze.game.objects.PathToFilesUtil.EMPTY_HALL;

public class EmptyHall implements GameObject {

    private Texture emptyHallTexture = new Texture(EMPTY_HALL);
    @Override
    public void addToMap() {

    }

    @Override
    public void render(SpriteBatch batch,int i,int j) {
        batch.begin();
        batch.draw(emptyHallTexture, i*20, j*20, 20,20);
        batch.end();
    }
}
