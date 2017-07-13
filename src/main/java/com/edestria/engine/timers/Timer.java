package com.edestria.engine.timers;

import com.edestria.engine.EdestriaEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Timer<T> {

    public interface TimerCompletion {

        void onCompletion();
    }

    private String name;
    private EdestriaEngine edestriaEngine;

    @Setter private TimerCompletion timerCompletion;

    public Timer(String name, EdestriaEngine edestriaEngine) {
        this.name = name;
        this.edestriaEngine = edestriaEngine;
    }

    public abstract void start();

    public abstract void stop();

    public abstract long getElapsedTime();

    public abstract String getElapsedTimeReadable();
}
