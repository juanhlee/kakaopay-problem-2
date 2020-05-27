package com.kakao.yebgi.server.exception;

import com.kakao.yebgi.server.constant.ApiError;
import lombok.Data;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public class ApiParameterException extends ApiException {
    @Getter
    private List<ErrorInfo> fields;

    public ApiParameterException(BindingResult result) {
        super(ApiError.INVALID_PARAMETER);
        this.fields = result
                .getAllErrors().stream()
                .map(error -> new ErrorInfo(error.getObjectName(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @Data
    public class ErrorInfo {
        private String field;

        private String message;

        ErrorInfo(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}