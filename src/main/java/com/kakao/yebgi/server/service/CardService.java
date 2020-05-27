package com.kakao.yebgi.server.service;

import com.kakao.yebgi.server.card.CardInfoMapper;
import com.kakao.yebgi.server.card.model.CardInfo;
import com.kakao.yebgi.server.constant.ApiError;
import com.kakao.yebgi.server.crypto.CipherProvider;
import com.kakao.yebgi.server.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class CardService {
    @Value("${card.cipher.type}")
    private String CIPHER_TYPE;

    @Value("${card.cipher.key}")
    private String CIPHER_KEY;

    @Value("${card.cipher.separator}")
    private String CIPHER_SEPARATOR;

    private CipherProvider cipherProvider;

    @PostConstruct
    void init() throws NoSuchAlgorithmException, InvalidKeySpecException {
        cipherProvider = new CipherProvider(CIPHER_TYPE, CIPHER_KEY);
    }

    public String encrypt(CardInfo cardInfo) throws ApiException {
        try {
            return cipherProvider.encrypt(CardInfoMapper.serialize(cardInfo));
        } catch (Exception e) {
            throw new ApiException(ApiError.ERROR, e.getMessage());
        }
    }

    public CardInfo decrypt(String encryptedCardInfo) throws ApiException {
        try {
            return CardInfoMapper.deserialize(cipherProvider.decrypt(encryptedCardInfo));
        } catch (Exception e) {
            throw new ApiException(ApiError.ERROR, e.getMessage());
        }
    }
}