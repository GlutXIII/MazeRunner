package com.maze.game.objects.factory;

import com.maze.game.objects.gameObjects.*;
import com.maze.game.objects.gameObjects.standard.*;
import com.maze.game.objects.gameObjects.winter.WinterEmptyHall;
import com.maze.game.objects.gameObjects.winter.WinterOpenDoors;
import com.maze.game.objects.gameObjects.winter.WinterWall;

import static com.maze.game.objects.utils.Constants.*;

public class GameObjectStringMap {
public static String getString(GameObject gameObject) {
    if (gameObject instanceof Wall || gameObject instanceof WinterWall) {
        return WALL_CODE;
    }
    if (gameObject instanceof EmptyHall || gameObject instanceof WinterEmptyHall) {
        return EMPTY_HALL_CODE;
    }
    if (gameObject instanceof Player) {
        return PLAYER_CODE;
    }
    if (gameObject instanceof VictoryPlace) {
        return VICTORY_PLACE_CODE;
    }
    if (gameObject instanceof Key){
        return KEY_CODE;
    }
    if (gameObject instanceof Doors){
        return DOORS_CODE;
    }
    if (gameObject instanceof OpenDoors || gameObject instanceof WinterOpenDoors){
        return OPEN_DOORS_CODE;
    }
    if (gameObject instanceof Enemy || gameObject instanceof Enemy){
        return ENEMY_CODE;
    }
    return null;
}
}
