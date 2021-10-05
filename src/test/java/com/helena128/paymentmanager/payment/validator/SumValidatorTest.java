package com.helena128.paymentmanager.payment.validator;

import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.exception.ExceptionMessage;
import com.helena128.paymentmanager.payment.exception.PaymentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class SumValidatorTest {

    @InjectMocks
    private SumValidator sumValidator;

    @Test
    public void testValidatingDtoWithCorrectSum() {
        PaymentDto paymentDto = PaymentDto.builder()
                .sum(BigDecimal.valueOf(10.02))
                .build();
        sumValidator.validate(paymentDto);
    }

    @Test
    public void testValidatingDtoWithSumEqualZero() {
        PaymentDto paymentDto = PaymentDto.builder()
                .sum(BigDecimal.valueOf(0.00))
                .build();
        Throwable ex = Assertions.assertThrows(PaymentException.class, () -> sumValidator.validate(paymentDto));
        Assertions.assertEquals(ExceptionMessage.NEGATIVE_SUM_MSG.getValue(), ex.getMessage());
    }

    @Test
    public void testValidatingDtoWithSumLessThanZero() {
        PaymentDto paymentDto = PaymentDto.builder()
                .sum(BigDecimal.valueOf(-10.00))
                .build();
        Throwable ex = Assertions.assertThrows(PaymentException.class, () -> sumValidator.validate(paymentDto));
        Assertions.assertEquals(ExceptionMessage.NEGATIVE_SUM_MSG.getValue(), ex.getMessage());
    }

}