package com.edestria.engine.timers.service;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.Purgeable;
import com.edestria.engine.timers.Countdown;
import com.edestria.engine.timers.Counter;
import com.edestria.engine.timers.Timer;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimerService implements Purgeable {

    private final EdestriaEngine edestriaEngine;

    private Map<String, Timer> timers = new HashMap<>();

    public TimerService(EdestriaEngine edestriaEngine) {
        this.edestriaEngine = edestriaEngine;
        this.timers = new HashMap<>();
    }

    @Builder(builderMethodName = "counterBuilder")
    private Counter createCounter(String name) {
        stopIfExists(name);
        this.timers.put(name.toLowerCase(), new Counter(name.toLowerCase(), this.edestriaEngine));
        return (Counter) this.timers.get(name.toLowerCase());
    }

    @Builder(builderMethodName = "countdownBuilder")
    private Countdown createCountdown(String name, TimeUnit timeUnit, int time, Timer.TimerCompletion timerCompletion) {
        stopIfExists(name);
        this.timers.put(name.toLowerCase(), new Countdown(name.toLowerCase(), this.edestriaEngine, timeUnit, time, timerCompletion));
        return (Countdown) this.timers.get(name.toLowerCase());
    }

    public void stopTimer(String name) {
        this.stopIfExists(name);
    }

    private void stopIfExists(String name) {
        if (this.timers.containsKey(name.toLowerCase())) {
            this.timers.get(name.toLowerCase()).stop();
        }
    }

    @Override
    public void purge() {
        this.timers.values().forEach(Timer::stop);
        this.timers.clear();
    }
}
