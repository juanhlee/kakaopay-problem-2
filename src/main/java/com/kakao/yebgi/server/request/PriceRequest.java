package com.kakao.yebgi.server.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.yebgi.server.validator.PriceGreaterThanVat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PriceGreaterThanVat(message = "금액을 VAT 보다 더 많은 숫자로 입력해주세요.")
public class PriceRequest {
    @NotNull(message = "금액을 입력해주세요.")
    @Min(value = 100, message = "금액을 {value}원 이상으로 입력해주세요.")
    @Max(value = 1000000000, message = "금액을 {value}원 이하로 입력해주세요.")
    private Long price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long vat;

    @JsonIgnore
    public Long getDefaultVat() {
        if (price != null) {
            return Optional
                    .ofNullable(vat)
                    .orElse(Math.round(price / 11.0));
        } else {
            return vat;
        }
    }
}