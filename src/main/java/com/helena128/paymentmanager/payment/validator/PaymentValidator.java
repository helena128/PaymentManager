package com.helena128.paymentmanager.payment.validator;

import com.helena128.paymentmanager.model.PaymentDto;

public interface PaymentValidator {

    void validate(PaymentDto paymentDto);
}
