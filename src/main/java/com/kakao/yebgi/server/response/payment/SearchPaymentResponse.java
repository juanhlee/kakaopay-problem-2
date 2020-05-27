package com.kakao.yebgi.server.response.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.kakao.yebgi.server.card.model.MaskedCardInfo;
import com.kakao.yebgi.server.constant.PaymentType;
import lombok.Builder;
import lombok.Data;

@Data
public class SearchPaymentResponse {
    private String id;

    private MaskedCardInfo cardInfo;

    private PaymentType paymentType;

    private Long price;

    private Long vat;

    @Builder
    public SearchPaymentResponse(String id, MaskedCardInfo cardInfo, PaymentType paymentType, Long price, Long vat) {
        this.id = id;
        this.cardInfo = cardInfo;
        this.paymentType = paymentType;
        this.price = price;
        this.vat = vat;
    }

    @JsonCreator
    private SearchPaymentResponse() {
        super();
    }
}