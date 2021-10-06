package com.helena128.paymentmanager.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode
public class CardInfo {

    @NotNull
    private String cardNumber;
    @NotNull
    private String expiryDate;
    @NotNull
    private String cvc; // 3-4 digits
}
