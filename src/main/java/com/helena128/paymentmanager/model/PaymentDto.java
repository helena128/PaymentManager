package com.helena128.paymentmanager.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
public class PaymentDto {

    private String cardHolderName;
    private String cardNumber;
    private LocalDate expiryDate;
    private String cvv;
    private Instant createdDateTime;
    private BigDecimal sum;
}
