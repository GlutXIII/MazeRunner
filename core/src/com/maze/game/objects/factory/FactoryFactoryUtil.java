package com.maze.game.objects.factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FactoryFactoryUtil {
    private static final Map<String,FactoryFactory> factoryMap =
            Collections.unmodifiableMap(new HashMap<String,FactoryFactory>() {{
                put("0", new FactoryFactory() { public AbstractFactory create() { return new FactoryUtil(); }});
                put("1", new FactoryFactory() { public AbstractFactory create() { return new WinterFactoryUtil(); }});
            }});

    public AbstractFactory createAbstractFactory(String action) throws Exception {
        FactoryFactory factory = factoryMap.get(action);
        if (factory == null) {
            throw new Exception();
        }
        return factory.create();
    }
}

