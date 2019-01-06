package org.github.unicon.web;

import org.github.unicon.conv.date.DateService;
import org.github.unicon.conv.measure.MeasureType;
import org.github.unicon.conv.measure.MeasureUnit;
import org.github.unicon.conv.measure.MeasuredValue;
import org.github.unicon.conv.measure.impl.DurationUnit;
import org.github.unicon.conv.text.EncodingType;
import org.github.unicon.conv.text.EscapeType;
import org.github.unicon.conv.text.HashType;
import org.github.unicon.conv.text.TextService;
import org.github.unicon.web.model.MeasureConvertResponse;
import org.github.unicon.web.model.MeasureUnitDto;
import org.github.unicon.web.model.MeasuresListResponse;
import org.github.unicon.web.model.ValueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/convert")
public class ConversionController {
    private static ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() ->
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
    );

    @Autowired
    private DateService dateService;

    @Autowired
    private TextService textService;

    @GetMapping(path = "/measure-list")
    @ResponseBody
    public MeasuresListResponse listMeasureTypes() {
        final Map<MeasureType, MeasureUnitDto[]> measureUnits = Arrays.stream(MeasureType.values())
            .collect(Collectors.toMap(
                x -> x,
                x -> Arrays.stream(x.getUnits())
                    .map(MeasureUnitDto::build)
                    .toArray(MeasureUnitDto[]::new)
            ));

        final MeasuresListResponse response = new MeasuresListResponse();
        response.setTypes(MeasureType.values());
        response.setMeasureUnits(measureUnits);
        return response;
    }

    @GetMapping(path = "/measure")
    @ResponseBody
    public MeasureConvertResponse convertMeasures(@RequestParam("type") MeasureType type,
                                                  @RequestParam("source") String sourceUnitName,
                                                  @RequestParam("target") String targetUnitName,
                                                  @RequestParam("value") BigDecimal value) {
        final MeasureUnit sourceUnit = type.getUnit(sourceUnitName);
        final MeasureUnit targetUnit = type.getUnit(targetUnitName);

        final MeasuredValue<MeasureUnit> result = new MeasuredValue<>(value, sourceUnit).to(targetUnit);
        return MeasureConvertResponse.build(type, result);
    }

    @GetMapping(path = "/date/toInterval")
    @ResponseBody
    public ValueResponse<BigDecimal> dateToInterval(@RequestParam("source") String sourceDate,
                                                    @RequestParam("target") String targetDate,
                                                    @RequestParam("durationUnit") String targetUnitName) {
        final MeasuredValue<DurationUnit> result;
        try {
            result = dateService.durationBetween(
                DATE_FORMAT.get().parse(sourceDate),
                DATE_FORMAT.get().parse(targetDate),
                (DurationUnit) MeasureType.DURATION.getUnit(targetUnitName)
            );

            return ValueResponse.build(result.getValue());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/date/toDate")
    @ResponseBody
    public ValueResponse<String> dateToDate(@RequestParam("source") String sourceDate,
                                            @RequestParam("interval") BigDecimal interval,
                                            @RequestParam("durationUnit") String targetUnitName) {
        final DurationUnit targetUnit = (DurationUnit) MeasureType.DURATION.getUnit(targetUnitName);

        final Date result;
        try {
            result = dateService.dateAfter(
                DATE_FORMAT.get().parse(sourceDate),
                new MeasuredValue<>(interval, targetUnit)
            );

            return ValueResponse.build(DATE_FORMAT.get().format(result));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/text/escape")
    @ResponseBody
    public ValueResponse<String> textEscape(@RequestParam("source") String source,
                                            @RequestParam("type") String type) {
        return ValueResponse.build(textService.escape(source, EscapeType.parse(type)));
    }

    @GetMapping(path = "/text/unescape")
    @ResponseBody
    public ValueResponse<String> textUnescape(@RequestParam("source") String source,
                                              @RequestParam("type") String type) {
        return ValueResponse.build(textService.unescape(source, EscapeType.parse(type)));
    }

    @GetMapping(path = "/text/encode")
    @ResponseBody
    public ValueResponse<String> textEncode(@RequestParam("source") String source,
                                            @RequestParam("sourceType") String sourceType,
                                            @RequestParam("targetType") String targetType) {
        final EncodingType sourceEnc = EncodingType.parse(sourceType);
        final EncodingType targetEnc = EncodingType.parse(targetType);

        final byte[] data = textService.decode(source, sourceEnc);
        return ValueResponse.build(textService.encode(data, targetEnc));
    }

    @GetMapping(path = "/text/hash")
    @ResponseBody
    public ValueResponse<String> textHash(@RequestParam("source") String source,
                                          @RequestParam("encoding") String encodingCode,
                                          @RequestParam("encodingTgt") String encodingTgtCode,
                                          @RequestParam("hashType") String hashTypeCode) {
        final EncodingType encoding = EncodingType.parse(encodingCode);
        final EncodingType encodingTgt = EncodingType.parse(encodingTgtCode);
        final HashType hashType = HashType.parse(hashTypeCode);

        final byte[] data = textService.decode(source, encoding);
        final byte[] hash = textService.hash(data, hashType);
        return ValueResponse.build(textService.encode(hash, encodingTgt));
    }
}
