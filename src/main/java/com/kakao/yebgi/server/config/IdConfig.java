package com.kakao.yebgi.server.config;

import com.kakao.yebgi.server.constant.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Callable;

@Configuration
public class IdConfig {
    @Bean(Constants.PAYLOAD_ID)
    public Callable<String> randomId() {
        return () -> RandomStringUtils.random(
                Constants.ID_SIZE,
                "0123456789abcdefghijklmnopqrstuvwxyzABCDEFHIJKLMNOPSRQTUVWXYZ"
        );
    }
}