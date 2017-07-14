package com.edestria.engine.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Time {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("E MMMM d y hh:mm a z");

    public static long toTicks(TimeUnit timeUnit, int duration) {
        return timeUnit.toMillis(duration) / 50;
    }

    public static String formatDate(Date date) {
        return Time.DATE_FORMAT.format(date);
    }
}
