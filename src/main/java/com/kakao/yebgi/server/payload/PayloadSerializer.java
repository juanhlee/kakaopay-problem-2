package com.kakao.yebgi.server.payload;

import com.kakao.yebgi.server.constant.Constants;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Optional;

@UtilityClass
public class PayloadSerializer {
    private static final int LENGTH_SIZE = 4;

    public static String serialize(Payload payload) {
        StringBuilder bodyBuilder = Optional
                .of(new StringBuilder())
                .map(value -> {
                    value.setLength(Constants.PAYLOAD_SIZE - LENGTH_SIZE);
                    return value;
                })
                .get();

        for (Field field : FieldUtils.getAllFields(payload.getClass())) {
            Optional
                    .ofNullable(field.getAnnotation(PayloadField.class))
                    .ifPresent(payloadField -> {
                        try {
                            int start = payloadField.start();
                            int end = payloadField.start() + payloadField.length();

                            String value = payloadField
                                    .formatter()
                                    .format(
                                            FieldUtils.readField(field, payload, true),
                                            payloadField.length()
                                    );

                            bodyBuilder.replace(start, end, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        return StringUtils.join(
                PayloadFormatter
                        .NUMBER
                        .format(bodyBuilder.length(), LENGTH_SIZE),
                bodyBuilder
        );
    }
}