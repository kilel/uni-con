package org.github.unicon.model.measure.unit;

import org.github.unicon.model.measure.MeasureUnit;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

public enum DurationUnit implements MeasureUnit {
    NANOSECOND(1, "nanosecond", NANOSECONDS),
    MICROSECOND(2, "microsecond", MICROSECONDS),
    MILLISECOND(3, "millisecond", MILLISECONDS),
    SECOND(4, "second", SECONDS),
    MINUTE(5, "minute", MINUTES),
    HOUR(6, "hour", HOURS),
    DAY(7, "day", DAYS),
    ;

    private final long id;
    private final String code;
    private final TimeUnit timeUnit;

    DurationUnit(long id, String code, TimeUnit timeUnit) {
        this.id = id;
        this.code = code;
        this.timeUnit = timeUnit;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
