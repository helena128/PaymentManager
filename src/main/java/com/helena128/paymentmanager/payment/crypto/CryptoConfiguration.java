package com.helena128.paymentmanager.payment.crypto;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoConfiguration {

    @Bean
    public StandardPBEStringEncryptor pbeStringEncryptor(final CryptoConfigurationProperties properties) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(properties.getPassword());
        encryptor.setAlgorithm(properties.getAlgorithm());
        return encryptor;
    }
}
