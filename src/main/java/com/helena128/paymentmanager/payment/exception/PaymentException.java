package com.helena128.paymentmanager.payment.exception;

public class PaymentException extends RuntimeException {

    public PaymentException(final ExceptionMessage message) {
        super(message.getValue());
    }
}
