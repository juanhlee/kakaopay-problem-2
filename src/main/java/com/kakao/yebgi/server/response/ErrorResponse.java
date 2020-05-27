package com.kakao.yebgi.server.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.yebgi.server.constant.ApiError;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private ApiError apiError;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;

    public ErrorResponse(ApiError apiError, String message, Object result) {
        this.apiError = apiError;
        this.message = message;
        this.result = result;
    }

    @JsonGetter
    private int getCode() {
        return apiError.getCode();
    }

    @JsonGetter
    private String getDescription() {
        return apiError.getDescription();
    }
}