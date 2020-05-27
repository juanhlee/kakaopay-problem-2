package com.kakao.yebgi.server.request.payment;

import com.kakao.yebgi.server.common.CommonTestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CancelPaymentRequestTest extends CommonTestCase {
    @Test
    public void 결제_ID_누락() {
        CancelPaymentRequest request = defaultRequest();
        request.setPaymentId(null);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 결제_ID_잘못된_길이() {
        CancelPaymentRequest request = defaultRequest();
        request.setPaymentId("abcde");
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 금액_누락() {
        CancelPaymentRequest request = defaultRequest();
        request.setPrice(null);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 금액_범위_미만() {
        CancelPaymentRequest request = defaultRequest();
        request.setPrice(10L);
        request.setVat(1L);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 금액_범위_초과() {
        CancelPaymentRequest request = defaultRequest();
        request.setPrice(5000000000L);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void VAT가_금액보다_많은_경우() {
        CancelPaymentRequest request = defaultRequest();
        request.setPrice(100L);
        request.setVat(10000L);
        assertEquals(validate(request).size(), 1);
    }

    private CancelPaymentRequest defaultRequest() {
        return new CancelPaymentRequest("wyFzgSLlDBUPKglu1umh", 11000L, 1000L);
    }
}