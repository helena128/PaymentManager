package com.helena128.paymentmanager.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PaymentDto {

    private String cardHolderName;
    private String cardNumber;
    private LocalDate localDate;
    private String cvv;
    private LocalDateTime createdDateTime;
    private BigDecimal sum;
}
