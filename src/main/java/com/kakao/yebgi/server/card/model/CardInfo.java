package com.kakao.yebgi.server.card.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfo {
    private String number;

    private String expiryDate;

    private String verificationCode;
}