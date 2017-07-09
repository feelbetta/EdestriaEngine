package com.edestria.engine.timers;

import com.edestria.engine.EdestriaEngine;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

public class Countdown extends Timer<Countdown> {

    private long start;
    private long elapsed;
    private BukkitTask task;

    public Countdown(String name, EdestriaEngine edestriaEngine, TimeUnit timeUnit, int time) {
        super(name, edestriaEngine);
        this.start = timeUnit.toNanos(time);
    }

    @Override
    public void start() {
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                elapsed = elapsed - (System.nanoTime() - start);
            }
        }.runTaskTimer(this.getEdestriaEngine(), 0, TimeUnit.SECONDS.toMillis(1) / 50);
    }

    @Override
    public void stop() {
        this.task.cancel();
    }

    @Override
    public long getElapsedTime() {
        return this.elapsed;
    }

    @Override
    public String getElapsedTimeReadable() {
        return DurationFormatUtils.formatDuration(this.elapsed, "HH:mm:ss");
    }
}
