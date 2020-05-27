package com.kakao.yebgi.server.response.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;

@Data
public class ApplyPaymentResponse {
    private String id;

    @Builder
    public ApplyPaymentResponse(String id) {
        this.id = id;
    }

    @JsonCreator
    private ApplyPaymentResponse() {
        super();
    }
}