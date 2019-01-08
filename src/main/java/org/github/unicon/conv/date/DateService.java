package org.github.unicon.conv.date;

import org.github.unicon.conv.measure.MeasuredValue;
import org.github.unicon.conv.measure.impl.DurationUnit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static org.github.unicon.conv.measure.impl.DurationUnit.*;

@Service
public class DateService {

    public MeasuredValue<DurationUnit> durationBetween(Date source, Date target, DurationUnit unit) {
        final BigDecimal datesDeltaMs = BigDecimal.valueOf(target.getTime() - source.getTime());
        return new MeasuredValue<>(datesDeltaMs, MILLISECOND).to(unit);
    }

    public Date dateShift(Date source, MeasuredValue<DurationUnit> value) {
        final MeasuredValue<DurationUnit> valueMs = value.to(MILLISECOND);
        return new Date(source.getTime() + valueMs.getValue().longValue());
    }
}
