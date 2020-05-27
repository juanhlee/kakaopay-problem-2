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
    @NotNull(message = "카드정보를 입력해주세요.")
    @Valid
    private CardRequest card;

    @NotNull(message = "할부개월을 입력해주세요.")
    @Min(value = 0, message = "할부개월은 {value} 이상으로 입력해주세요.")
    @Max(value = 12, message = "할부개월은 {value} 이하로 입력해주세요.")
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