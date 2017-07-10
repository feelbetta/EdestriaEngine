package com.edestria.engine.timers;

import com.edestria.engine.EdestriaEngine;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

public class Countdown extends Timer<Countdown> {

    public interface CountdownCompletion {

        void onFinish();
    }

    private long start;
    private long elapsed;
    private BukkitTask task;

    private CountdownCompletion countdownCompletion;

    public Countdown(String name, EdestriaEngine edestriaEngine, TimeUnit timeUnit, int time, CountdownCompletion countdownCompletion) {
        super(name, edestriaEngine);
        this.start = timeUnit.toMillis(time);
        this.countdownCompletion = countdownCompletion;
    }

    @Override
    public void start() {
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                elapsed = getTimeLeft() > 1000 ? (start - 2000) - (start -=TimeUnit.SECONDS.toMillis(1)) : start;
                if (getTimeLeft() > 1) {
                    return;
                }
                cancel();
                if (countdownCompletion == null) {
                    return;
                }
                countdownCompletion.onFinish();
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

    public long getTimeLeft() {
        // 10000 - 9000
        return this.start - this.elapsed;
    }

    public String getTimeLeftReadable() {
        return DurationFormatUtils.formatDuration(this.start - this.elapsed, "HH:mm:ss");
    }

    public void setCountdownCompletion(CountdownCompletion countdownCompletion) {
        this.countdownCompletion = countdownCompletion;
    }
}
