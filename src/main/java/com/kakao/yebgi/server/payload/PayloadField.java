package com.kakao.yebgi.server.payload;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface PayloadField {
    PayloadFormatter formatter();

    int start();

    int length();
}