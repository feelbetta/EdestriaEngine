package com.edestria.engine.utils.time;

import java.util.concurrent.TimeUnit;

public class Time {

    public static long toTicks(TimeUnit timeUnit, int duration) {
        return timeUnit.toMillis(duration) / 50;
    }
}
