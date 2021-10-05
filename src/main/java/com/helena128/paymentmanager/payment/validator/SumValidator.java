package com.helena128.paymentmanager.payment.validator;

import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.exception.ExceptionMessage;
import com.helena128.paymentmanager.payment.exception.PaymentException;

import java.math.BigDecimal;

public class SumValidator implements PaymentValidator {

    @Override
    public void validate(final PaymentDto paymentDto) {
        if (BigDecimal.ZERO.compareTo(paymentDto.getSum()) >= 0) {
            throw new PaymentException(ExceptionMessage.NEGATIVE_SUM_MSG);
        }
    }
}
