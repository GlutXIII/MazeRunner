package com.maze.game.objects.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.maze.game.objects.PathToFilesUtil.VICTORY_PLACE;

public class VictoryPlace implements GameObject {
    private Texture victoryPlaceTexture = new Texture(VICTORY_PLACE);
    @Override
    public void addToMap() {

    }

    @Override
    public void render(SpriteBatch batch,int i,int j) {
        batch.begin();
        batch.draw(victoryPlaceTexture, i*20, j*20, 20,20);
        batch.end();
    }
}
