package com.helena128.paymentmanager.payment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResponseConstants {
    MESSAGE("message"), STATUS("status");

    @Getter
    private final String value;
}
