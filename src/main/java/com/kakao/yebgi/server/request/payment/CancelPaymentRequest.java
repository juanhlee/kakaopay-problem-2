package com.kakao.yebgi.server.request.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.kakao.yebgi.server.constant.Constants;
import com.kakao.yebgi.server.request.PriceRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class CancelPaymentRequest extends PriceRequest {
    @NotNull(message = "{validation.constraints.paymentId.notNull}")
    @Size(min = Constants.ID_SIZE, max = Constants.ID_SIZE, message = "{validation.constraints.paymentId.size}")
    private String paymentId;

    @Builder
    public CancelPaymentRequest(String paymentId, Long price, Long vat) {
        super(price, vat);
        this.paymentId = paymentId;
    }

    @JsonCreator
    private CancelPaymentRequest() {
        super();
    }
}