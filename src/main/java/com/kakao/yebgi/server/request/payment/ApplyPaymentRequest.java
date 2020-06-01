package com.kakao.yebgi.server.request.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.kakao.yebgi.server.request.CardRequest;
import com.kakao.yebgi.server.request.PriceRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApplyPaymentRequest extends PriceRequest {
    @NotNull(message = "{validation.constraints.cardRequest.notNull}")
    @Valid
    private CardRequest card;

    @NotNull(message = "{validation.constraints.paymentMonths.notNull}")
    @Min(value = 0, message = "{validation.constraints.paymentMonths.min}")
    @Max(value = 12, message = "{validation.constraints.paymentMonths.max}")
    private Integer months;

    @Builder
    public ApplyPaymentRequest(CardRequest card, Integer months, Long price, Long vat) {
        super(price, vat);
        this.card = card;
        this.months = months;
    }

    @JsonCreator
    private ApplyPaymentRequest() {
        super();
    }
}