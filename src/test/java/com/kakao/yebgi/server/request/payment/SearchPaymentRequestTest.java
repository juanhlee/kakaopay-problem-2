package com.kakao.yebgi.server.request.payment;

import com.kakao.yebgi.server.common.CommonTestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchPaymentRequestTest extends CommonTestCase {
    @Test
    public void ID_누락() {
        SearchPaymentRequest request = defaultRequest();
        request.setId(null);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void ID_잘못된_길이() {
        SearchPaymentRequest request = defaultRequest();
        request.setId("abcde");
        assertEquals(validate(request).size(), 1);
    }

    private SearchPaymentRequest defaultRequest() {
        return new SearchPaymentRequest("wyFzgSLlDBUPKglu1umh");
    }
}