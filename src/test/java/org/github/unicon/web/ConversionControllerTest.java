package org.github.unicon.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.unicon.TestUtils;
import org.github.unicon.UniconApplicationTests;
import org.github.unicon.web.api.model.BaseResponse;
import org.github.unicon.web.api.model.MeasureConvertResponse;
import org.github.unicon.web.api.model.ResultStatus;
import org.github.unicon.web.api.model.ValueResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MimeTypeUtils;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class ConversionControllerTest extends UniconApplicationTests {
    private static final double DELTA = 0.001;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void convertMeasuresTest() throws Exception {
        MeasureConvertResponse response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/measure")
                                .param("type", "LENGTH")
                                .param("source", "meter")
                                .param("target", "kilometer")
                                .param("value", "456")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                        .andReturn().getResponse()
                        .getContentAsString(),
                MeasureConvertResponse.class);

        TestUtils.assertNotDifferMuch(BigDecimal.valueOf(0.456), response.getValue(), DELTA);
    }

    @Test
    public void wrongValueMeasureTest() throws Exception {
        BaseResponse response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/measure")
                                .param("type", "LENGTH")
                                .param("source", "meters")
                                .param("target", "kilometer")
                                .param("value", "456")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                BaseResponse.class);

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("Unknown unit [meters]"));
    }

    @Test
    public void missingParameterMeasureTest() throws Exception {
        BaseResponse response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/measure")
                                .param("type", "LENGTH")
                                .param("source", "meter")
                                .param("target", "kilometer")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                BaseResponse.class);

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'value' is not present"));
    }

    @Test
    public void wrongParameterMeasureTest() throws Exception {
        BaseResponse response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/measure")
                                .param("type", "LENGTH")
                                .param("sourse", "meter")
                                .param("target", "kilometer")
                                .param("value", "456")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                BaseResponse.class);

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'source' is not present"));
    }

    @Test
    public void dateToIntervalTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/date/toInterval")
                                .param("source", "2019-01-01 00:00:00.000+0300")
                                .param("target", "2019-01-02 12:00:00.000+0300")
                                .param("durationUnit", "DAY")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        TestUtils.assertNotDifferMuch(BigDecimal.valueOf(1.5), response.getValue(), DELTA);
    }

    @Test
    public void wrongValueIntervalTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/date/toInterval")
                                .param("source", "2019-01-01 00:00:00.000+0300")
                                .param("target", "2019-01-02 12:00:00.000+0300")
                                .param("durationUnit", "days")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("Unknown unit [days]"));
    }

    @Test
    public void missingParameterIntervalTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/date/toInterval")
                                .param("source", "2019-01-01 00:00:00.000+0300")
                                .param("target", "2019-01-02 12:00:00.000+0300")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'durationUnit' is not present"));
    }

    @Test
    public void wrongParameterIntervalTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/date/toInterval")
                                .param("source", "2019-01-01 00:00:00.000+0300")
                                .param("target", "2019-01-02 12:00:00.000+0300")
                                .param("duration", "days")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'durationUnit' is not present"));
    }

    @Test
    public void dateToDateTest() throws Exception {
        ValueResponse<String> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/date/toDate")
                                .param("source", "2019-01-01 00:00:00.000+0300")
                                .param("interval", "1.5")
                                .param("durationUnit", "DAY")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<String>>(){});

        Assert.assertEquals("2019-01-02 12:00:00.000+0300", response.getValue());
    }

    @Test
    public void wrongValueDateTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/date/toDate")
                                .param("source", "2019-01-01 00:00:00.000+0300")
                                .param("interval", "1.5")
                                .param("durationUnit", "days")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("Unknown unit [days]"));
    }

    @Test
    public void missingParameterDateTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/date/toDate")
                                .param("source", "2019-01-01 00:00:00.000+0300")
                                .param("interval", "1.5")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'durationUnit' is not present"));
    }

    @Test
    public void wrongParameterDateTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/date/toDate")
                                .param("source", "2019-01-01 00:00:00.000+0300")
                                .param("interval", "1.5")
                                .param("duration", "DAY")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'durationUnit' is not present"));
    }

    @Test
    public void textEscapeTest() throws Exception {
        ValueResponse<String> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/escape")
                                .param("source", "text_dont_escape")
                                .param("type", "URL")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<String>>(){});

        Assert.assertEquals("text_dont_escape", response.getValue());
    }

    @Test
    public void wrongValueEscapeTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/escape")
                                .param("source", "text_dont_escape")
                                .param("type", "URI")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertEquals("Unknown escape type", response.getMessage());
    }

    @Test
    public void missingParameterEscapeTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/escape")
                                .param("source", "text_dont_escape")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'type' is not present"));
    }

    @Test
    public void wrongParameterEscapeTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/escape")
                                .param("source", "text_dont_escape")
                                .param("typo", "URL")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'type' is not present"));
    }

    @Test
    public void textUnescapeTest() throws Exception {
        ValueResponse<String> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/unescape")
                                .param("source", "text_dont_escape")
                                .param("type", "URL")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<String>>(){});

        Assert.assertEquals("text_dont_escape", response.getValue());
    }

    @Test
    public void wrongValueUnescapeTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/unescape")
                                .param("source", "text_dont_escape")
                                .param("type", "URI")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertEquals("Unknown escape type", response.getMessage());
    }

    @Test
    public void missingParameterUnescapeTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/unescape")
                                .param("source", "text_dont_escape")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'type' is not present"));
    }

    @Test
    public void wrongParameterUnescapeTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/unescape")
                                .param("source", "text_dont_escape")
                                .param("typo", "URL")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'type' is not present"));
    }

    @Test
    public void textEncodeTest() throws Exception {
        ValueResponse<String> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/encode")
                                .param("source", "some test text")
                                .param("sourceType", "PLAIN")
                                .param("targetType", "HEX")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<String>>(){});

        Assert.assertEquals("736f6d6520746573742074657874", response.getValue());
    }

    @Test
    public void wrongValueEncodeTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/encode")
                                .param("source", "some test text")
                                .param("sourceType", "PLAIN")
                                .param("targetType", "HEXx")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertEquals("Unknown encoding type", response.getMessage());
    }

    @Test
    public void missingParameterEncodeTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/encode")
                                .param("source", "some test text")
                                .param("sourceType", "PLAIN")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'targetType' is not present"));
    }

    @Test
    public void wrongParameterEncodeTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/encode")
                                .param("source", "some test text")
                                .param("sourceType", "PLAIN")
                                .param("target", "HEX")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'targetType' is not present"));
    }

    @Test
    public void textHashTest() throws Exception {
        ValueResponse<String> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/hash")
                                .param("source", "serdgsrtysrthgbxcv cv r5t")
                                .param("encoding", "PLAIN")
                                .param("encodingTgt", "HEX")
                                .param("hashType", "MD5")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<String>>(){});

        Assert.assertEquals("f6340d9534a3b87669c7abc5106370b4", response.getValue());
    }

    @Test
    public void wrongValueHashTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/hash")
                                .param("source", "serdgsrtysrthgbxcv cv r5t")
                                .param("encoding", "PLAIN")
                                .param("encodingTgt", "HEX")
                                .param("hashType", "MD55")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertEquals("Unknown hash type", response.getMessage());
    }

    @Test
    public void missingParameterHashTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/hash")
                                .param("source", "serdgsrtysrthgbxcv cv r5t")
                                .param("encoding", "PLAIN")
                                .param("encodingTgt", "HEX")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'hashType' is not present"));
    }

    @Test
    public void wrongParameterHashTest() throws Exception {
        ValueResponse<BigDecimal> response = mapper.readValue(
                mockMvc.perform(
                        get("/api/convert/text/hash")
                                .param("source", "serdgsrtysrthgbxcv cv r5t")
                                .param("encoding", "PLAIN")
                                .param("encodingTgt", "HEX")
                                .param("hash", "MD5")
                                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                        .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                        .andReturn().getResponse()
                        .getContentAsString(),
                new TypeReference<ValueResponse<BigDecimal>>(){});

        Assert.assertEquals(ResultStatus.FAIL, response.getStatus());
        Assert.assertTrue(response.getMessage().contains("parameter 'hashType' is not present"));
    }
}
