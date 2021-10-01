package com.helena128.paymentmanager.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;

@Data
public class PaymentDto {

    private String id;
    private String cardHolderName;
    private String cardHolderEmail;
    private String cardNumber;
    private LocalDate expiryDate;
    private String cvc;
    private String recipient;
    private BigDecimal sum;
    private Instant createdDateTime;
}
