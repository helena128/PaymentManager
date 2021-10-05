package com.helena128.paymentmanager.payment.validator;

import com.helena128.paymentmanager.model.CardInfo;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.exception.ExceptionMessage;
import com.helena128.paymentmanager.payment.exception.PaymentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LuhnCardNumberValidatorTest {

    @InjectMocks
    private LuhnCardNumberValidator luhnCardNumberValidator;

    @Test
    public void testCorrectLuhnCardNumber() {
        luhnCardNumberValidator.validate(PaymentDto.builder()
                .cardInfo(CardInfo.builder()
                        .cardNumber("12345674")
                        .build())
                .build());
    }

    @Test
    public void testIncorrectLuhnCardNumber() {
        PaymentException paymentException = Assertions.assertThrows(PaymentException.class, () -> luhnCardNumberValidator.validate(PaymentDto.builder()
                .cardInfo(CardInfo.builder()
                        .cardNumber("12345678")
                        .build())
                .build()));
        Assertions.assertEquals(ExceptionMessage.INVALID_CARD_NUMBER.getValue(), paymentException.getMessage());
    }
}