package com.helena128.paymentmanager.payment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    INVALID_CARD_NUMBER_MSG("Invalid card number!"),
    NEGATIVE_SUM_MSG("Sum of payment cannot be below 0!"),
    INCORRECT_DATE_MSG("Incorrect date format, please use yyyy-mm-dd");

    private final String value;
}
