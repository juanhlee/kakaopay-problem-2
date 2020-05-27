package com.kakao.yebgi.server.request;

import com.kakao.yebgi.server.common.CommonTestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardRequestTest extends CommonTestCase {
    @Test
    public void 카드_번호_누락() {
        CardRequest request = defaultCardRequest();
        request.setNumber(null);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 카드_번호_길이부족() {
        CardRequest request = defaultCardRequest();
        request.setNumber("12345");
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 카드_번호_길이초과() {
        CardRequest request = defaultCardRequest();
        request.setNumber("12345678901234567889012345");
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 카드_유효기간_누락() {
        CardRequest request = defaultCardRequest();
        request.setExpiryDate(null);
        assertEquals(validate(request).size(), 1);
    }


    @Test
    public void 카드_유효기간_잘못된_패턴() {
        CardRequest request = defaultCardRequest();
        request.setExpiryDate("abcd");
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 카드_CVC_누락() {
        CardRequest request = defaultCardRequest();
        request.setVerificationCode(null);
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 카드_CVC_잘못된_패턴() {
        CardRequest request = defaultCardRequest();
        request.setVerificationCode("12345678901234567889012345");
        assertEquals(validate(request).size(), 1);
    }

    @Test
    public void 카드_CVC_잘못된_길이() {
        CardRequest request = defaultCardRequest();
        request.setVerificationCode("12345");
        assertEquals(validate(request).size(), 1);
    }
}