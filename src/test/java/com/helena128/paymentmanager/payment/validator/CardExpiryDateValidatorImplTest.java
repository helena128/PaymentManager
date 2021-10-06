package com.helena128.paymentmanager.payment.validator;

import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.exception.ExceptionMessage;
import com.helena128.paymentmanager.payment.exception.PaymentException;
import com.helena128.paymentmanager.payment.util.TestDataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardExpiryDateValidatorImplTest {

    @InjectMocks
    private CardExpiryDateValidatorImpl cardExpiryDateValidator;

    @Test
    public void testCorrectExpiryData() {
        PaymentDto paymentDto = TestDataHelper.buildPaymentDto();
        cardExpiryDateValidator.validate(paymentDto);
    }

    @Test
    public void testIncorrectExpiryData() {
        PaymentDto paymentDto = TestDataHelper.buildPaymentDto();
        paymentDto.getCardInfo().setExpiryDate("22-03-1556");
        Throwable ex = Assertions.assertThrows(PaymentException.class, () -> cardExpiryDateValidator.validate(paymentDto));
        Assertions.assertEquals(ExceptionMessage.INCORRECT_DATE_MSG.getValue(), ex.getMessage());
    }

}