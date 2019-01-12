package org.github.unicon.conv.text;

import java.util.Arrays;

public enum EncodingType {
    PLAIN("plain"),
    BIN("bin"),
    OCT("oct"),
    OCT_PER_BYTE("oct-per-byte"),
    DEC("dec"),
    DEC_PER_BYTE("dec-per-byte"),
    HEX("hex"),
    BASE32("base-32"),
    BASE64("base-64"),
    ;

    private final String code;

    EncodingType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static EncodingType parse(String code) {
        return Arrays.stream(values())
            .filter(x -> x.code.equalsIgnoreCase(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown encoding type"));
    }
}
