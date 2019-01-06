package com.maze.game.objects.factory;

import com.maze.game.objects.gameObjects.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WinterFactoryUtil implements AbstractFactory {
    private static final Map<String,GameObjectFactory> factoryMap =
            Collections.unmodifiableMap(new HashMap<String,GameObjectFactory>() {{
                put("0", new GameObjectFactory() { public GameObject create() { return new WinterEmptyHall(); }});
                put("1", new GameObjectFactory() { public GameObject create() { return new Player(); }});
                put("2", new GameObjectFactory() { public GameObject create() { return new WinterWall(); }});
                put("3", new GameObjectFactory() { public GameObject create() { return new VictoryPlace(); }});
            }});

    public GameObject createGameObject(String action) throws Exception {
        GameObjectFactory factory = factoryMap.get(action);
        if (factory == null) {
            throw new Exception();
        }
        return factory.create();
    }
}
