package com.helena128.paymentmanager.payment.crypto.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("crypto")
@Getter
@Setter
public class CryptoConfigurationProperties {
    private String password;
    private String algorithm;
}
