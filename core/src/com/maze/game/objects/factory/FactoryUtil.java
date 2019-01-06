package com.maze.game.objects.factory;

import com.maze.game.objects.gameObjects.*;
import com.maze.game.objects.gameObjects.standard.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.maze.game.objects.utils.Constants.*;

public class FactoryUtil implements AbstractFactory {
    private static final Map<String,GameObjectFactory> factoryMap =
            Collections.unmodifiableMap(new HashMap<String,GameObjectFactory>() {{
        put(EMPTY_HALL_CODE, new GameObjectFactory() { public GameObject create() { return new EmptyHall(); }});
        put(PLAYER_CODE, new GameObjectFactory() { public GameObject create() { return new Player(); }});
        put(WALL_CODE, new GameObjectFactory() { public GameObject create() { return new Wall(); }});
        put(VICTORY_PLACE_CODE, new GameObjectFactory() { public GameObject create() { return new VictoryPlace(); }});
        put(KEY_CODE, new GameObjectFactory() { public GameObject create() { return new Key(); }});
        put(DOORS_CODE, new GameObjectFactory() { public GameObject create() { return new Doors(); }});
        put(OPEN_DOORS_CODE, new GameObjectFactory() { public GameObject create() { return new OpenDoors(); }});



            }});

public GameObject createGameObject(String action) throws Exception {
    GameObjectFactory factory = factoryMap.get(action);
        if (factory == null) {
        throw new Exception();
        }
        return factory.create();
        }
}
