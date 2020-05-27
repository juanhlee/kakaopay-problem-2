package com.kakao.yebgi.server.card;

import com.kakao.yebgi.server.card.model.CardInfo;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class CardInfoMapper {
    private static final String DELIMITER = "|";

    public static String serialize(CardInfo cardInfo) {
        return StringUtils.join(
                Arrays.asList(
                        cardInfo.getNumber(),
                        cardInfo.getExpiryDate(),
                        cardInfo.getVerificationCode()
                ),
                DELIMITER
        );
    }

    public static CardInfo deserialize(String input) {
        return Optional
                .of(StringUtils.split(input, DELIMITER))
                .map(args -> {
                    CardInfo cardInfo = new CardInfo();
                    cardInfo.setNumber(args[0]);
                    cardInfo.setExpiryDate(args[1]);
                    cardInfo.setVerificationCode(args[2]);
                    return cardInfo;
                })
                .get();
    }
}