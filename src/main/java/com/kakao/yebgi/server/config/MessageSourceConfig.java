package com.kakao.yebgi.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {
    @Value("${message.source.basename}")
    private String BASE_NAME;

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename(BASE_NAME);
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}