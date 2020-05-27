package com.kakao.yebgi.server.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.yebgi.server.request.CardRequest;
import com.kakao.yebgi.server.request.payment.ApplyPaymentRequest;
import com.kakao.yebgi.server.request.payment.CancelPaymentRequest;
import com.kakao.yebgi.server.request.payment.SearchPaymentRequest;
import com.kakao.yebgi.server.response.ErrorResponse;
import com.kakao.yebgi.server.response.payment.ApplyPaymentResponse;
import com.kakao.yebgi.server.response.payment.CancelPaymentResponse;
import com.kakao.yebgi.server.response.payment.SearchPaymentResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommonTestCase {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    protected CardRequest defaultCardRequest() {
        return new CardRequest("1234567890", "0120", "123");
    }

    protected <T> Collection<ConstraintViolation<T>> validate(T object) {
        Collection<ConstraintViolation<T>> violations = Validation
                .buildDefaultValidatorFactory()
                .getValidator()
                .validate(object);

        for (ConstraintViolation<T> violation : violations) {
            System.err.println(violation.getMessage());
        }

        return violations;
    }

    protected Object doApply(ApplyPaymentRequest request, ResultMatcher resultMatcher) throws Throwable {
        try {
            Object response = perform(
                    "/api/payment/v1/apply",
                    request,
                    resultMatcher,
                    ApplyPaymentResponse.class
            );

            System.err.println(String.format("Price = %d, vat = %d : 결제 성공", request.getPrice(), request.getVat()));
            return response;
        } catch (Exception e) {
            System.err.println(String.format("Price = %d, vat = %d : 결제 실패 (%s)", request.getPrice(), request.getVat(), e.getMessage()));
            throw ExceptionUtils.getRootCause(e);
        }
    }

    protected Object doCancel(CancelPaymentRequest request, ResultMatcher resultMatcher) throws Throwable {
        try {
            Object response = perform(
                    "/api/payment/v1/cancel",
                    request,
                    resultMatcher,
                    CancelPaymentResponse.class
            );

            System.err.println(String.format("Price = %d, vat = %d : 취소 성공", request.getPrice(), request.getVat()));
            return response;
        } catch (Exception e) {
            System.err.println(String.format("Price = %d, vat = %d : 취소 실패 (%s)", request.getPrice(), request.getVat(), e.getMessage()));
            throw ExceptionUtils.getRootCause(e);
        }
    }

    protected Object doSearch(SearchPaymentRequest request, ResultMatcher resultMatcher) throws Throwable {
        try {
            Object response = perform(
                    "/api/payment/v1/search",
                    request,
                    resultMatcher,
                    SearchPaymentResponse.class
            );

            System.err.println(String.format("조회 성공 : %s", objectMapper.writeValueAsString(response)));
            return response;
        } catch (Exception e) {
            System.err.println(String.format("조회 실패 (%s)", e.getMessage()));
            throw ExceptionUtils.getRootCause(e);
        }
    }

    private <T> Object perform(String url, T request, ResultMatcher resultMatcher, Class<?> clazz) throws Throwable {
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(request))
        );

        if (resultMatcher != null) {
            resultActions.andExpect(resultMatcher);
        }

        MvcResult result = resultActions
                .andDo(print())
                .andReturn();

        HttpStatus httpStatus = HttpStatus.valueOf(
                result
                        .getResponse()
                        .getStatus()
        );

        String content = result
                .getResponse()
                .getContentAsString();

        if (httpStatus == HttpStatus.OK) {
            return objectMapper.readValue(content, clazz);
        } else {
            return objectMapper.readValue(content, ErrorResponse.class);
        }
    }
}