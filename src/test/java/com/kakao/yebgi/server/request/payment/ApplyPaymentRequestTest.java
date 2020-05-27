package com.kakao.yebgi.server.request.payment;

import com.kakao.yebgi.server.common.CommonTestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplyPaymentRequestTest extends CommonTestCase {
    @Test
    public void 카드_정보_누락() {
        ApplyPaymentRequest request = defaultRequest();
        request.setCard(null);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 할부개월_누락() {
        ApplyPaymentRequest request = defaultRequest();
        request.setMonths(null);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 할부개월_범위_미만() {
        ApplyPaymentRequest request = defaultRequest();
        request.setMonths(-1);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 할부개월_범위_초과() {
        ApplyPaymentRequest request = defaultRequest();
        request.setMonths(20);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 금액_누락() {
        ApplyPaymentRequest request = defaultRequest();
        request.setPrice(null);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 금액_범위_미만() {
        ApplyPaymentRequest request = defaultRequest();
        request.setPrice(10L);
        request.setVat(1L);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 금액_범위_초과() {
        ApplyPaymentRequest request = defaultRequest();
        request.setPrice(5000000000L);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void VAT가_금액보다_많은_경우() {
        ApplyPaymentRequest request = defaultRequest();
        request.setPrice(100L);
        request.setVat(10000L);
        assertEquals(validate(request).size(), 1);
    }

    private ApplyPaymentRequest defaultRequest() {
        return new ApplyPaymentRequest(defaultCardRequest(), 0, 11000L, 1000L);
    }
}