package com.kakao.yebgi.server.request.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.kakao.yebgi.server.constant.Constants;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SearchPaymentRequest {
    @NotNull(message = "ID를 입력해주세요.")
    @Size(min = Constants.ID_SIZE, max = Constants.ID_SIZE, message = "ID를 {max}자리 숫자로 입력해주세요.")
    private String id;

    @Builder
    public SearchPaymentRequest(String id) {
        this.id = id;
    }

    @JsonCreator
    private SearchPaymentRequest() {
        super();
    }
}