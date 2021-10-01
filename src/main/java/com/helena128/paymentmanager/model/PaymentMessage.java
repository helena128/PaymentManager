package com.helena128.paymentmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMessage {

    private String id;
    private CardHolderInfo cardHolderInfo;
    private String cardNumber;
    private String recipient;
    private BigDecimal sum;
    private Instant createdDateTime;
}
