package com.helena128.paymentmanager.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class CardInfo {

    private String cardNumber;
    private String expiryDate;
    private String cvc; // 3-4 digits
}
