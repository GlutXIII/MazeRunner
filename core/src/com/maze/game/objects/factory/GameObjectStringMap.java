package com.maze.game.objects.factory;

import com.maze.game.objects.gameObjects.*;

public class GameObjectStringMap {
public static String getString(GameObject gameObject) {
    if (gameObject instanceof Wall || gameObject instanceof WinterWall) {
        return "2";
    }
    if (gameObject instanceof EmptyHall || gameObject instanceof WinterEmptyHall) {
        return "0";
    }
    if (gameObject instanceof Player) {
        return "1";
    }
    if (gameObject instanceof VictoryPlace) {
        return "3";
    }
    return null;
}
}
