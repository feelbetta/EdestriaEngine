package com.edestria.engine.data;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.Purgeable;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DataManager<T> implements Purgeable {

    private final EdestriaEngine edestriaEngine;

    private final Map<String, T> data = new HashMap<>();

    public DataManager(EdestriaEngine edestriaEngine) {
        this.edestriaEngine = edestriaEngine;
    }

    @Override
    public void purge() {
        this.data.clear();
    }
}
