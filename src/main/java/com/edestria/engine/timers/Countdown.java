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

    public Countdown(String name, EdestriaEngine edestriaEngine, TimeUnit timeUnit, int time, TimerCompletion timerCompletion) {
        super(name, edestriaEngine);
        this.start = timeUnit.toMillis(time);
        this.setTimerCompletion(timerCompletion);
    }

    @Override
    public void start() {
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                Countdown.this.elapsed = getTimeLeft() > 1000 ? (Countdown.this.start - 2000) - (Countdown.this.start -=TimeUnit.SECONDS.toMillis(1)) : Countdown.this.start;
                if (getTimeLeft() > 1) {
                    return;
                }
                cancel();
                if (getTimerCompletion() == null) {
                    return;
                }
                getTimerCompletion().onCompletion();
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
        return DurationFormatUtils.formatDuration(this.elapsed, Timer.TIME_FORMAT);
    }

    public long getTimeLeft() {
        return this.start - this.elapsed;
    }

    public String getTimeLeftReadable() {
        return DurationFormatUtils.formatDuration(this.start - this.elapsed, Timer.TIME_FORMAT);
    }
}
