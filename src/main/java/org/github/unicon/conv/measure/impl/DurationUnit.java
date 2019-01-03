package org.github.unicon.conv.measure.impl;

import org.github.unicon.conv.measure.ScalableMeasureUnit;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

public enum DurationUnit implements ScalableMeasureUnit<DurationUnit> {
    NANOSECOND("second", "Nanosecond", NANOSECONDS, BigDecimal.ONE),
    MICROSECOND("microsecond", "Microsecond", MICROSECONDS, NANOSECOND.multiplier.multiply(BigDecimal.valueOf(1000))),
    MILLISECOND("millisecond", "Millisecond", MILLISECONDS, MICROSECOND.multiplier.multiply(BigDecimal.valueOf(1000))),
    SECOND("second", "Second", SECONDS, MILLISECOND.multiplier.multiply(BigDecimal.valueOf(1000))),
    MINUTE("minute", "Minute", MINUTES, SECOND.multiplier.multiply(BigDecimal.valueOf(60))),
    HOUR("hour", "Hour", HOURS, MINUTE.multiplier.multiply(BigDecimal.valueOf(60))),
    DAY("day", "Day", DAYS, HOUR.multiplier.multiply(BigDecimal.valueOf(24))),
    ;


    private final String code;
    private final String descr;
    private final TimeUnit timeUnit;
    private final BigDecimal multiplier;

    DurationUnit(String code, String descr, TimeUnit timeUnit, BigDecimal multiplier) {
        this.code = code;
        this.descr = descr;
        this.timeUnit = timeUnit;
        this.multiplier = multiplier;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescr() {
        return descr;
    }


    @Override
    public BigDecimal getMultiplier() {
        return multiplier;
    }
}
