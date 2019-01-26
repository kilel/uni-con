package org.github.unicon.conv.date;

import org.github.unicon.TestUtils;
import org.github.unicon.UniconApplicationTests;
import org.github.unicon.model.measure.MeasuredValue;
import org.github.unicon.model.measure.unit.DurationUnit;
import org.github.unicon.service.DateService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateServiceTest extends UniconApplicationTests {
    private static final double DELTA = 0.000001;

    @Autowired
    private DateService dateService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

    @Test
    public void simpleIntervalTest() throws ParseException {
        String sourceDate = "2019-01-01 00:00:00.000+0300";
        String targetDate = "2019-01-02 12:00:00.000+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.DAY, 1.5);
        assertDateInterval(sourceDate, targetDate, DurationUnit.HOUR, 36);
        assertDateInterval(sourceDate, targetDate, DurationUnit.MINUTE, 2160);
        assertDateInterval(sourceDate, targetDate, DurationUnit.SECOND, 129600);
        assertDateInterval(sourceDate, targetDate, DurationUnit.MILLISECOND, 129600000);
        assertDateInterval(sourceDate, targetDate, DurationUnit.MICROSECOND, new BigDecimal("129600000000"));
        assertDateInterval(sourceDate, targetDate, DurationUnit.NANOSECOND, new BigDecimal("129600000000000"));

    }

    @Test
    public void changeMonthIntervalTest() throws ParseException {
        String sourceDate = "2019-01-31 00:00:00.000+0300";
        String targetDate = "2019-02-02 12:00:00.000+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.MINUTE, 3600);
    }

    @Test
    public void changeYearIntervalTest() throws ParseException {
        String sourceDate = "2019-01-31 00:00:00.000+0300";
        String targetDate = "2020-02-01 12:00:00.000+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.HOUR, 8796);
    }

    @Test
    public void leapYearIntervalTest() throws ParseException {
        String sourceDate = "2020-02-28 00:00:00.000+0300";
        String targetDate = "2020-03-01 12:00:00.000+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.DAY, 2.5);
    }

    @Test
    public void littleIntervalTest() throws ParseException {
        String sourceDate = "2019-01-31 12:00:00.000+0300";
        String targetDate = "2019-01-31 12:01:01.500+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.SECOND, 61.5);
        assertDateInterval(sourceDate, targetDate, DurationUnit.NANOSECOND, new BigDecimal("61500000000"));
    }

    @Test
    public void negativeIntervalTest() throws ParseException {
        String sourceDate = "2019-01-31 12:01:01.000+0300";
        String targetDate = "2019-01-31 12:00:00.500+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.SECOND, -60.5);
        assertDateInterval(sourceDate, targetDate, DurationUnit.NANOSECOND, new BigDecimal("-60500000000"));
    }

    @Test
    public void zeroIntervalTest() throws ParseException {
        String sourceDate = "2019-01-31 12:00:00.500+0300";
        String targetDate = "2019-01-31 12:00:00.500+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.SECOND, 0);
        assertDateInterval(sourceDate, targetDate, DurationUnit.NANOSECOND, 0);
    }

    @Test
    public void bigTargetIntervalTest() throws ParseException {
        String sourceDate = "2019-01-31 12:00:00.500+0300";
        String targetDate = "3019-01-31 12:00:00.500+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.DAY, 365242);
    }

    @Test
    @Ignore ("fix later")
    public void littleSourceIntervalTest() throws ParseException {
        String sourceDate = "1019-01-31 12:00:00.500+0300";
        String targetDate = "2019-01-31 12:00:00.500+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.DAY, 365243);
    }

    @Test
    public void goodOldDaysIntervalTest() throws ParseException {
        String sourceDate = "1019-01-31 12:00:00.500+0300";
        String targetDate = "1019-03-31 12:00:00.500+0300";

        assertDateInterval(sourceDate, targetDate, DurationUnit.DAY, 59);
    }

    @Test
    public void simpleDateTest() throws ParseException {
        String sourceDate = "2019-01-01 00:00:00.000+0300";
        BigDecimal duration = new BigDecimal("1.5");
        assertDateShift(sourceDate, duration, DurationUnit.DAY, "2019-01-02 12:00:00.000+0300");
        assertDateShift(sourceDate, duration, DurationUnit.HOUR, "2019-01-01 01:30:00.000+0300");
        assertDateShift(sourceDate, duration, DurationUnit.MINUTE, "2019-01-01 00:01:30.000+0300");
        assertDateShift(sourceDate, duration, DurationUnit.SECOND, "2019-01-01 00:00:01.500+0300");
        assertDateShift(sourceDate, duration, DurationUnit.MILLISECOND, "2019-01-01 00:00:00.001+0300");
        assertDateShift(sourceDate, duration, DurationUnit.MICROSECOND, "2019-01-01 00:00:00.000+0300");
        assertDateShift(sourceDate, duration, DurationUnit.NANOSECOND, "2019-01-01 00:00:00.000+0300");
    }

    @Test
    public void changeYearDateTest() throws ParseException {
        String sourceDate = "2019-01-01 00:00:00.000+0300";
        assertDateShift(sourceDate, new BigDecimal("366.5"), DurationUnit.DAY, "2020-01-02 12:00:00.000+0300");
        assertDateShift(sourceDate, new BigDecimal("31665600000000"), DurationUnit.MICROSECOND, "2020-01-02 12:00:00.000+0300");
    }

    @Test
    public void leapYearDateTest() throws ParseException {
        String sourceDate = "2020-02-28 00:00:00.000+0300";
        assertDateShift(sourceDate, new BigDecimal("1"), DurationUnit.DAY, "2020-02-29 00:00:00.000+0300");
    }

    @Test
    public void changeMonthDateTest() throws ParseException {
        String sourceDate = "2019-01-01 00:00:00.000+0300";
        assertDateShift(sourceDate, new BigDecimal("33"), DurationUnit.DAY, "2019-02-03 00:00:00.000+0300");
        assertDateShift(sourceDate, new BigDecimal("2851200"), DurationUnit.SECOND, "2019-02-03 00:00:00.000+0300");
    }

    public void assertDateInterval(String sourceDate, String targetDate, DurationUnit unit, double expected) throws ParseException {
        final MeasuredValue<DurationUnit> result = dateService.durationBetween(
                dateFormat.parse(sourceDate),
                dateFormat.parse(targetDate),
                unit
        );

        Assert.assertEquals(expected, result.getValue().doubleValue(), DELTA);
    }

    public void assertDateInterval(String sourceDate, String targetDate, DurationUnit unit, BigDecimal expected) throws ParseException {
        final MeasuredValue<DurationUnit> result = dateService.durationBetween(
                dateFormat.parse(sourceDate),
                dateFormat.parse(targetDate),
                unit
        );

        TestUtils.assertNotDifferMuch(expected, result.getValue(), DELTA);
    }

    public void assertDateShift(String sourceDate, BigDecimal duration, DurationUnit unit, String expected) throws ParseException {
        final Date result = dateService.dateShift(
                dateFormat.parse(sourceDate),
                new MeasuredValue<>(duration, unit)
        );
        Assert.assertEquals(expected, dateFormat.format(result));
    }
}
