package com.kakao.yebgi.server.request.payment;

import com.kakao.yebgi.server.common.CommonTestCase;
import org.junit.Test;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchPaymentRequestTest extends CommonTestCase {
    @Test
    public void ID_누락() {
        SearchPaymentRequest request = defaultRequest();
        request.setId(null);
        assertConstraint(request, NotNull.class);
    }

    @Test
    public void ID_잘못된_길이() {
        SearchPaymentRequest request = defaultRequest();
        request.setId("abcde");
        assertConstraint(request, Size.class);
    }

    private SearchPaymentRequest defaultRequest() {
        return new SearchPaymentRequest("wyFzgSLlDBUPKglu1umh");
    }
}