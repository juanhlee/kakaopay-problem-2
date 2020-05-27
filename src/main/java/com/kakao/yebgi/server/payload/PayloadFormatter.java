package com.kakao.yebgi.server.payload;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public enum PayloadFormatter {
    NUMBER() {
        @Override
        String onFormat(Object input, int length) {
            return StringUtils.leftPad(String.valueOf(input), length, " ");
        }
    },
    NUMBER_0() {
        @Override
        String onFormat(Object input, int length) {
            return StringUtils.leftPad(String.valueOf(input), length, "0");
        }
    },
    NUMBER_L() {
        @Override
        String onFormat(Object input, int length) {
            return StringUtils.rightPad(String.valueOf(input), length, " ");
        }
    },
    STRING() {
        @Override
        String onFormat(Object input, int length) {
            return StringUtils.rightPad(String.valueOf(input), length, " ");
        }
    };

    public String format(Object input, int length) {
        String value = Optional
                .ofNullable(input)
                .map(String::valueOf)
                .orElse("");
        return onFormat(value, length);
    }

    abstract String onFormat(Object input, int length);
}