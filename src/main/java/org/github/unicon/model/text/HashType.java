package org.github.unicon.model.text;

import java.util.Arrays;

public enum HashType {
    MD5("md5"),
    SHA_1("sha-1"),
    SHA_256("sha-256"),
    SHA_512("sha-215"),
    SHA_3("sha-3"),
    ;

    private final String code;

    HashType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static HashType parse(String code) {
        return Arrays.stream(values())
            .filter(x -> x.code.equalsIgnoreCase(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown hash type"));
    }
}
