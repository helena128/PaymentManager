package com.helena128.paymentmanager.payment.mapper;

import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.model.PaymentMessage;
import com.helena128.paymentmanager.payment.crypto.config.CryptoConfigurationProperties;
import com.helena128.paymentmanager.payment.entity.PaymentEntity;
import com.helena128.paymentmanager.payment.util.TestDataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ContextConfiguration(classes = PaymentMapperTest.PaymentMapperTestConfig.class)
public class PaymentMapperTest {

    @Autowired
    private PaymentMapper paymentMapper;

    @Test
    public void testFieldsEncodedWhenMappingToPaymentEntity() {
        PaymentDto paymentDto = TestDataHelper.buildPaymentDto();
        PaymentEntity paymentEntity = paymentMapper.paymentDtoToEntity(paymentDto);
        Assertions.assertEquals(paymentDto.getSum(), paymentEntity.getSum());
        Assertions.assertNotNull(paymentEntity.getCardInfo());
        Assertions.assertNotEquals(paymentDto.getCardInfo().getCardNumber(), paymentEntity.getCardInfo().getCardNumber());
        Assertions.assertNotEquals(paymentDto.getCardInfo().getCvc(), paymentEntity.getCardInfo().getCvc());
        Assertions.assertNotEquals(paymentDto.getCardInfo().getExpiryDate(), paymentEntity.getCardInfo().getExpiryDate());
    }

    @Test
    public void testFieldsDecodedWhenMappingToMessage() {
        PaymentEntity paymentEntity = TestDataHelper.buildPaymentEntity();
        PaymentMessage paymentMessage = paymentMapper.paymentEntityToPaymentMessage(paymentEntity);
        Assertions.assertEquals(paymentEntity.getId(), paymentMessage.getId());
        Assertions.assertTrue(paymentMessage.getCardNumber().startsWith(PaymentMapper.CARD_TEMPLATE));
        Assertions.assertEquals(paymentEntity.getSum(), paymentMessage.getSum());
    }

    @Configuration
    @ComponentScan(basePackages = {"com.helena128.paymentmanager.payment.mapper",
            "com.helena128.paymentmanager.payment.crypto"})
    public static class PaymentMapperTestConfig {

        @Bean
        public CryptoConfigurationProperties cryptoConfigurationProperties() {
            CryptoConfigurationProperties cryptoConfigurationProperties = new CryptoConfigurationProperties();
            cryptoConfigurationProperties.setPassword("Password123!");
            cryptoConfigurationProperties.setAlgorithm("PBEWithMD5AndTripleDES");
            return cryptoConfigurationProperties;
        }

    }
}