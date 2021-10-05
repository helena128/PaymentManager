package com.helena128.paymentmanager.payment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    INVALID_CARD_NUMBER("Invalid card number!");

    private final String value;
}
