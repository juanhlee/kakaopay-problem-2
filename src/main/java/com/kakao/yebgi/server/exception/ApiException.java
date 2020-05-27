package com.kakao.yebgi.server.exception;

import com.kakao.yebgi.server.constant.ApiError;
import lombok.Getter;

public class ApiException extends Exception {
    @Getter
    private ApiError error;

    public ApiException(ApiError error) {
        this(error, null);
    }

    public ApiException(ApiError error, String message) {
        super(message);
        this.error = error;
    }
}