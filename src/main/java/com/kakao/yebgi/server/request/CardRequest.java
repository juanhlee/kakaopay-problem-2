package com.kakao.yebgi.server.request;

import com.kakao.yebgi.server.card.model.CardInfo;
import com.kakao.yebgi.server.validator.CardExpiryDate;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@NoArgsConstructor
public class CardRequest extends CardInfo {
    @NotNull(message = "카드번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]+", message = "카드번호를 숫자로 입력해주세요.")
    @Size(min = 10, max = 16, message = "카드번호는 {min} ~ {max}자리 숫자로 입력해야 합니다.")
    @Override
    public String getNumber() {
        return super.getNumber();
    }

    @CardExpiryDate(message = "카드유효기간은 앞에 2자리는 월, 뒤에는 년도의 마지막 2자리로 입력해야 합니다.")
    @Override
    public String getExpiryDate() {
        return super.getExpiryDate();
    }

    @NotNull(message = "CVC를 입력해주세요.")
    @Pattern(regexp = "[0-9]+", message = "CVC를 숫자로 입력해주세요.")
    @Size(min = 3, max = 3, message = "CVC를 {max}자리 숫자로 입력해주세요.")
    @Override
    public String getVerificationCode() {
        return super.getVerificationCode();
    }

    @Builder
    public CardRequest(String number, String expiryDate, String verificationCode) {
        super(number, expiryDate, verificationCode);
    }
}