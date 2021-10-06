package com.helena128.paymentmanager.payment.validator;

import com.helena128.paymentmanager.model.CardInfo;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.exception.ExceptionMessage;
import com.helena128.paymentmanager.payment.exception.PaymentException;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CardExpiryDateValidatorImpl implements PaymentValidator {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public void validate(final PaymentDto paymentDto) {
        final String expiryDate = Optional.ofNullable(paymentDto)
                .map(PaymentDto::getCardInfo)
                .map(CardInfo::getExpiryDate)
                .orElse(null);
        if (Objects.isNull(expiryDate) || !isValidFormat(expiryDate)) {
            throw new PaymentException(ExceptionMessage.INCORRECT_DATE_MSG);
        }
    }

    private boolean isValidFormat(final String expiryDate) {
        return GenericValidator.isDate(expiryDate, DATE_PATTERN, true);
    }
}
