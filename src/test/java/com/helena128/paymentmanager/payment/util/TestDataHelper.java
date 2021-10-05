package com.helena128.paymentmanager.payment.util;

import com.helena128.paymentmanager.model.CardHolderInfo;
import com.helena128.paymentmanager.model.CardInfo;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.entity.PaymentEntity;

import java.math.BigDecimal;
import java.util.UUID;

public final class TestDataHelper {

    public static PaymentDto buildPaymentDto() {
        return PaymentDto.builder()
                .cardHolderInfo(CardHolderInfo.builder().email("test@mail.com").name("Test Test").build())
                .recipient("Recipient name")
                .sum(new BigDecimal("100.2"))
                .cardInfo(CardInfo.builder().cvc("123").cardNumber("12345674").expiryDate("2020-03-02").build())
                .build();
    }

    public static PaymentEntity buildPaymentEntity() {
        return PaymentEntity.builder()
                .id(UUID.randomUUID().toString())
                .cardHolderInfo(CardHolderInfo.builder().email("test@mail.com").name("Test Test").build())
                .recipient("Recipient name")
                .sum(new BigDecimal("100.2"))
                .cardInfo(CardInfo.builder().cvc("123").cardNumber("12345674").expiryDate("2020-03-02").build())
                .build();
    }


    private TestDataHelper() {

    }
}
