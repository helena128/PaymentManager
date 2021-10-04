package com.helena128.paymentmanager.notification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("notification.email")
@Getter
@Setter
public class MailPropertiesConfiguration {
    private String username;
    private String password;
}
