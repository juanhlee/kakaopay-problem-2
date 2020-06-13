package com.kakao.yebgi.server.request;

import com.kakao.yebgi.server.common.CommonTestCase;
import org.junit.Assert;
import org.junit.Test;

public class PriceRequestTest extends CommonTestCase {
    @Test
    public void 금액_20000원_자동_VAT_계산_테스트() {
        PriceRequest request = new PriceRequest(20000L, null);
        Assert.assertEquals(request.getDefaultVat(), 1818L);
    }

    @Test
    public void 금액_7000원_자동_VAT_계산_테스트() {
        PriceRequest request = new PriceRequest(7000L, null);
        Assert.assertEquals(request.getDefaultVat(), 636L);
    }

    @Test
    public void 금액_6600원_자동_VAT_계산_테스트() {
        PriceRequest request = new PriceRequest(6600L, null);
        Assert.assertEquals(request.getDefaultVat(), 600L);
    }

    @Test
    public void 금액_3300원_자동_VAT_계산_테스트() {
        PriceRequest request = new PriceRequest(3300L, null);
        Assert.assertEquals(request.getDefaultVat(), 300L);
    }
}