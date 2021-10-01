package com.helena128.paymentmanager.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardInfo {

    private String cardNumber;
    private LocalDate expiryDate;
    private String cvc; // 3-4 digits
}
