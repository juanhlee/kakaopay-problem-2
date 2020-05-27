package com.kakao.yebgi.server;

import com.kakao.yebgi.server.common.CommonTestCase;
import com.kakao.yebgi.server.request.payment.ApplyPaymentRequest;
import com.kakao.yebgi.server.request.payment.SearchPaymentRequest;
import com.kakao.yebgi.server.response.payment.ApplyPaymentResponse;
import com.kakao.yebgi.server.response.payment.SearchPaymentResponse;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchTest extends CommonTestCase {
    @Test
    public void 조회_테스트() throws Throwable {
        ApplyPaymentResponse paymentResponse = (ApplyPaymentResponse) doApply(
                new ApplyPaymentRequest(defaultCardRequest(), 0, 11000L, 1000L),
                status().isOk()
        );

        SearchPaymentRequest searchRequest = SearchPaymentRequest
                .builder()
                .id(paymentResponse.getId())
                .build();

        SearchPaymentResponse searchResponse = (SearchPaymentResponse) doSearch(
                searchRequest,
                status().isOk()
        );

        assertEquals(paymentResponse.getId(), searchResponse.getId());
    }
}