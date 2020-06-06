package com.kakao.yebgi.server;

import com.kakao.yebgi.server.common.CommonTestCase;
import com.kakao.yebgi.server.constant.ApiError;
import com.kakao.yebgi.server.request.payment.ApplyPaymentRequest;
import com.kakao.yebgi.server.request.payment.CancelPaymentRequest;
import com.kakao.yebgi.server.request.payment.SearchPaymentRequest;
import com.kakao.yebgi.server.response.ErrorResponse;
import com.kakao.yebgi.server.response.payment.ApplyPaymentResponse;
import com.kakao.yebgi.server.response.payment.SearchPaymentResponse;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentTest extends CommonTestCase {
    @Test
    public void 전체취소_테스트() throws Throwable {
        ApplyPaymentResponse paymentResponse = (ApplyPaymentResponse) doApply(
                new ApplyPaymentRequest(defaultCardRequest(), 0, 11000L, 1000L),
                status().isOk()
        );

        assertNotNull(paymentResponse);
        assertCancelSuccess(new CancelPaymentRequest(paymentResponse.getId(), 11000L, 1000L));
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 0L, 0L);
    }

    @Test
    public void 전체취소_이후_다시취소_불가능_테스트() throws Throwable {
        ApplyPaymentResponse paymentResponse = (ApplyPaymentResponse) doApply(
                new ApplyPaymentRequest(defaultCardRequest(), 0, 11000L, 1000L),
                status().isOk()
        );

        assertNotNull(paymentResponse);

        assertCancelSuccess(new CancelPaymentRequest(paymentResponse.getId(), 11000L, 1000L));
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 0L, 0L);

        assertCancelFailed(
                new CancelPaymentRequest(paymentResponse.getId(), 1000L, 100L),
                ApiError.NOT_ENOUGH_PRICE
        );
    }

    @Test
    public void 부분취소_테스트1() throws Throwable {
        ApplyPaymentResponse paymentResponse = (ApplyPaymentResponse) doApply(
                new ApplyPaymentRequest(defaultCardRequest(), 0, 11000L, 1000L),
                status().isOk()
        );

        assertNotNull(paymentResponse);

        assertCancelSuccess(new CancelPaymentRequest(paymentResponse.getId(), 1100L, 100L));
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 9900L, 900L);

        assertCancelSuccess(new CancelPaymentRequest(paymentResponse.getId(), 3300L, null));
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 6600L, 600L);

        assertCancelFailed(
                new CancelPaymentRequest(paymentResponse.getId(), 7000L, null),
                ApiError.NOT_ENOUGH_PRICE
        );
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 6600L, 600L);

        assertCancelFailed(
                new CancelPaymentRequest(paymentResponse.getId(), 6600L, 700L),
                ApiError.NOT_ENOUGH_VAT
        );
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 6600L, 600L);

        assertCancelSuccess(new CancelPaymentRequest(paymentResponse.getId(), 6600L, 600L));
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 0L, 0L);

        assertCancelFailed(
                new CancelPaymentRequest(paymentResponse.getId(), 100L, null),
                ApiError.NOT_ENOUGH_PRICE
        );
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 0L, 0L);
    }

    @Test
    public void 부분취소_테스트2() throws Throwable {
        ApplyPaymentResponse paymentResponse = (ApplyPaymentResponse) doApply(
                new ApplyPaymentRequest(defaultCardRequest(), 0, 20000L, 909L),
                status().isOk()
        );

        assertNotNull(paymentResponse);
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 20000L, 909L);

        assertCancelSuccess(new CancelPaymentRequest(paymentResponse.getId(), 10000L, 0L));
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 10000L, 909L);

        assertCancelFailed(
                new CancelPaymentRequest(paymentResponse.getId(), 10000L, 0L),
                ApiError.VAT_GREATER_THAN_PRICE
        );
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 10000L, 909L);

        assertCancelSuccess(new CancelPaymentRequest(paymentResponse.getId(), 10000L, 909L));
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 0L, 0L);
    }

    @Test
    public void 부분취소_테스트3() throws Throwable {
        ApplyPaymentResponse paymentResponse = (ApplyPaymentResponse) doApply(
                new ApplyPaymentRequest(defaultCardRequest(), 0, 20000L, null),
                status().isOk()
        );

        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 20000L, 1818L);

        assertCancelSuccess(new CancelPaymentRequest(paymentResponse.getId(), 10000L, 1000L));
        assertSearchSuccess(new SearchPaymentRequest(paymentResponse.getId()), 10000L, 818L);

        assertCancelFailed(
                new CancelPaymentRequest(paymentResponse.getId(), 10000L, 909L),
                ApiError.NOT_ENOUGH_VAT
        );
        assertCancelSuccess(new CancelPaymentRequest(paymentResponse.getId(), 10000L, null));
    }

    private void assertSearchSuccess(SearchPaymentRequest request, Long price, Long vat) throws Throwable {
        SearchPaymentResponse response = (SearchPaymentResponse) super.doSearch(
                request,
                status().isOk()
        );

        assertEquals(response.getPrice(), price);
        assertEquals(response.getVat(), vat);
    }

    private void assertCancelSuccess(CancelPaymentRequest request) throws Throwable {
        assertNotNull(
                super.doCancel(
                        request,
                        status().isOk()
                )
        );
    }

    private void assertCancelFailed(CancelPaymentRequest request, ApiError expectedCode) throws Throwable {
        ErrorResponse result = (ErrorResponse) super.doCancel(
                request,
                status().is4xxClientError()
        );

        assertSame(expectedCode, result.getApiError());
    }
}