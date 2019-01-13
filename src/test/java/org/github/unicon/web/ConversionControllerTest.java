package org.github.unicon.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.unicon.TestUtils;
import org.github.unicon.UniconApplicationTests;
import org.github.unicon.web.api.model.MeasureConvertResponse;
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
}
