package com.maze.game.objects.factory;

import com.maze.game.objects.gameObjects.GameObject;

public interface AbstractFactory {
    GameObject createGameObject(String action) throws Exception;
}
