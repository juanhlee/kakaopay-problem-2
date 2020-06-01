package com.kakao.yebgi.server.request.payment;

import com.kakao.yebgi.server.common.CommonTestCase;
import com.kakao.yebgi.server.validator.PriceGreaterThanVat;
import org.junit.Test;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CancelPaymentRequestTest extends CommonTestCase {
    @Test
    public void 결제_ID_누락() {
        CancelPaymentRequest request = defaultRequest();
        request.setPaymentId(null);
        assertConstraint(request, NotNull.class);
    }

    @Test
    public void 결제_ID_잘못된_길이() {
        CancelPaymentRequest request = defaultRequest();
        request.setPaymentId("abcde");
        assertConstraint(request, Size.class);
    }

    @Test
    public void 금액_누락() {
        CancelPaymentRequest request = defaultRequest();
        request.setPrice(null);
        assertConstraint(request, NotNull.class);
    }

    @Test
    public void 금액_범위_미만() {
        CancelPaymentRequest request = defaultRequest();
        request.setPrice(10L);
        request.setVat(1L);
        assertConstraint(request, Min.class);
    }

    @Test
    public void 금액_범위_초과() {
        CancelPaymentRequest request = defaultRequest();
        request.setPrice(5000000000L);
        assertConstraint(request, Max.class);
    }

    @Test
    public void VAT가_금액보다_많은_경우() {
        CancelPaymentRequest request = defaultRequest();
        request.setPrice(100L);
        request.setVat(10000L);
        assertConstraint(request, PriceGreaterThanVat.class);
    }

    private CancelPaymentRequest defaultRequest() {
        return new CancelPaymentRequest("wyFzgSLlDBUPKglu1umh", 11000L, 1000L);
    }
}