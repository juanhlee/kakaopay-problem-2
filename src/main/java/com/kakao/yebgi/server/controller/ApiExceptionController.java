package com.kakao.yebgi.server.controller;

import com.kakao.yebgi.server.exception.ApiException;
import com.kakao.yebgi.server.exception.ApiParameterException;
import com.kakao.yebgi.server.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApiExceptionController {
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(
                                e.getError(),
                                e.getLocalizedMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(ApiParameterException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> handleApiException(ApiParameterException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(
                                e.getError(),
                                e.getLocalizedMessage(),
                                e.getFields()
                        )
                );
    }
}