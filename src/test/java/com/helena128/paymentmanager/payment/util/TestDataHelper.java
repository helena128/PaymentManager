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
                .cardInfo(CardInfo.builder()
                        .cvc("4jt9Zj487BRC6dE9RdzRD6yxHIQLSRWNSjhJA/Zlhz87l68v0bW8qLDunFrNiuoJ")
                        .cardNumber("VKB1vftZbRTtlY+0XlMxbk3T3hNu3nSkDy2s7ItuRfLAdtvyJKWW4la36rsOelb9")
                        .expiryDate("6yunA0A800NULocXJfrusBLQ5DhhD75rhBAx576tG4G0Ic/4YsnSyZ3xD5y4UaLo")
                        .build())
                .build();
    }


    private TestDataHelper() {

    }
}
