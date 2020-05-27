package com.kakao.yebgi.server.crypto;

import lombok.Getter;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Optional;

public class CipherProvider {
    @Getter
    private String type;

    @Getter
    private SecretKey secretKey;

    public CipherProvider(String type, String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.type = type;
        this.secretKey = SecretKeyFactory
                .getInstance(type)
                .generateSecret(new SecretKeySpec(key.getBytes(), type));
    }

    public String encrypt(String input) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return Base64
                .getEncoder()
                .encodeToString(cipher.doFinal(input.getBytes()));
    }

    public String decrypt(String input) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return Optional
                .of(
                        cipher.doFinal(
                                Base64
                                        .getDecoder()
                                        .decode(input)
                        )
                )
                .map(String::new)
                .orElse(null);
    }

    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(type);
    }
}