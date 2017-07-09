package com.edestria.engine.timers;

import com.edestria.engine.EdestriaEngine;
import lombok.Getter;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;

public class Counter extends Timer<Counter> {

    @Getter private StopWatch stopWatch;

    public Counter(String name, EdestriaEngine edestriaEngine) {
        super(name, edestriaEngine);
        this.stopWatch = new StopWatch();
    }

    @Override
    public void start() {
        this.stopWatch.start();
    }

    @Override
    public void stop() {
        this.stopWatch.suspend();
    }

    @Override
    public long getElapsedTime() {
        return stopWatch.getTime();
    }

    @Override
    public String getElapsedTimeReadable() {
        return DurationFormatUtils.formatDuration(this.stopWatch.getTime(), "HH:mm:ss");
    }

    public void pause() {
        this.stopWatch.stop();
    }
}
