package com.edestria.engine.timers;

import com.edestria.engine.EdestriaEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public abstract class Timer<T> {

    private String name;
    private EdestriaEngine edestriaEngine;

    public abstract void start();

    public abstract void stop();

    public abstract long getElapsedTime();

    public abstract String getElapsedTimeReadable();
}
