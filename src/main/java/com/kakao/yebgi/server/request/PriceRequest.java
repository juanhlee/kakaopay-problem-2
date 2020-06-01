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
@PriceGreaterThanVat(message = "{validation.constraints.priceGreaterThanVat}")
public class PriceRequest {
    @NotNull(message = "{validation.constraints.price.notNull}")
    @Min(value = 100, message = "{validation.constraints.price.min}")
    @Max(value = 1000000000, message = "{validation.constraints.price.max}")
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