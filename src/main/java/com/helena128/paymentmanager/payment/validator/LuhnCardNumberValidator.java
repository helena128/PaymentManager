package com.helena128.paymentmanager.payment.validator;

import com.helena128.paymentmanager.model.CardInfo;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.exception.ExceptionMessage;
import com.helena128.paymentmanager.payment.exception.PaymentException;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LuhnCardNumberValidator implements PaymentValidator {

    @Override
    public void validate(final PaymentDto paymentDto) {
        final String cardNumber = Optional.ofNullable(paymentDto.getCardInfo())
                .map(CardInfo::getCardNumber).orElse(null);
        if (!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(cardNumber)) {
            throw new PaymentException(ExceptionMessage.INVALID_CARD_NUMBER_MSG);
        }
    }
}
