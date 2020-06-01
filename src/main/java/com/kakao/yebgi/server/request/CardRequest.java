package com.kakao.yebgi.server.request;

import com.kakao.yebgi.server.card.model.CardInfo;
import com.kakao.yebgi.server.validator.CardExpiryDate;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
public class CardRequest extends CardInfo {
    @NotNull(message = "{validation.constraints.cardNumber.notNull}")
    @Pattern(regexp = "[0-9]+", message = "{validation.constraints.cardNumber.pattern}")
    @Size(min = 10, max = 16, message = "{validation.constraints.cardNumber.size}")
    @Override
    public String getNumber() {
        return super.getNumber();
    }

    @CardExpiryDate(message = "{validation.constraints.cardExpiryDate}")
    @Override
    public String getExpiryDate() {
        return super.getExpiryDate();
    }

    @NotNull(message = "{validation.constraints.cvc.notNull}")
    @Pattern(regexp = "[0-9]+", message = "{validation.constraints.cvc.pattern}")
    @Size(min = 3, max = 3, message = "{validation.constraints.cvc.size}")
    @Override
    public String getVerificationCode() {
        return super.getVerificationCode();
    }

    @Builder
    public CardRequest(String number, String expiryDate, String verificationCode) {
        super(number, expiryDate, verificationCode);
    }
}