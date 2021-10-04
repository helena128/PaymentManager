package com.helena128.paymentmanager.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentDto {

    private String id;
    private CardHolderInfo cardHolderInfo;
    private CardInfo cardInfo;
    private String recipient;
    private BigDecimal sum;
    private Instant createdDateTime;
}
