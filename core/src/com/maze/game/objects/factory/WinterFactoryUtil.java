package com.maze.game.objects.factory;

import com.maze.game.objects.gameObjects.*;
import com.maze.game.objects.gameObjects.standard.Player;
import com.maze.game.objects.gameObjects.standard.VictoryPlace;
import com.maze.game.objects.gameObjects.winter.WinterEmptyHall;
import com.maze.game.objects.gameObjects.winter.WinterKey;
import com.maze.game.objects.gameObjects.winter.WinterWall;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.maze.game.objects.utils.Constants.*;

public class WinterFactoryUtil implements AbstractFactory {
    private static final Map<String,GameObjectFactory> factoryMap =
            Collections.unmodifiableMap(new HashMap<String,GameObjectFactory>() {{
                put(EMPTY_HALL_CODE, new GameObjectFactory() { public GameObject create() { return new WinterEmptyHall(); }});
                put(PLAYER_CODE, new GameObjectFactory() { public GameObject create() { return new Player(); }});
                put(WALL_CODE, new GameObjectFactory() { public GameObject create() { return new WinterWall(); }});
                put(VICTORY_PLACE_CODE, new GameObjectFactory() { public GameObject create() { return new VictoryPlace(); }});
                put(KEY_CODE, new GameObjectFactory() { public GameObject create() { return new WinterKey(); }});

            }});

    public GameObject createGameObject(String action) throws Exception {
        GameObjectFactory factory = factoryMap.get(action);
        if (factory == null) {
            throw new Exception();
        }
        return factory.create();
    }
}
