package com.maze.game.objects.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameObject {
    void addToMap();
    void render(SpriteBatch spriteBatch,int i, int j);

}
