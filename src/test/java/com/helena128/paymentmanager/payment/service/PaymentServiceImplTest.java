package com.helena128.paymentmanager.payment.service;

import com.helena128.paymentmanager.model.CardInfo;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.model.PaymentMessage;
import com.helena128.paymentmanager.payment.entity.PaymentEntity;
import com.helena128.paymentmanager.payment.exception.ExceptionMessage;
import com.helena128.paymentmanager.payment.exception.PaymentException;
import com.helena128.paymentmanager.payment.kafka.PaymentMessageProducer;
import com.helena128.paymentmanager.payment.mapper.PaymentMapper;
import com.helena128.paymentmanager.payment.repository.PaymentRepository;
import com.helena128.paymentmanager.payment.util.TestDataHelper;
import com.helena128.paymentmanager.payment.validator.LuhnCardNumberValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentMessageProducer paymentMessageProducer;
    @Mock
    private PaymentRepository paymentRepository;
    @InjectMocks
    private PaymentServiceImpl paymentService;
    @Mock
    private PaymentMapper paymentMapper;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(paymentService, "paymentValidatorList", List.of(new LuhnCardNumberValidator()));
    }

    @Test
    public void testSavingDtoFailedValidation() {
        PaymentDto paymentDto = PaymentDto.builder()
                .cardInfo(CardInfo.builder().cardNumber("12345678").build())
                .build();
        StepVerifier.create(paymentService.createPayment(paymentDto))
                .expectErrorMatches(ex -> ex instanceof PaymentException
                        && ExceptionMessage.INVALID_CARD_NUMBER_MSG.getValue().equals(ex.getMessage()))
                .verify();
    }

    @Test
    public void testSavingDto() {
        Mockito.when(paymentRepository.save(Mockito.any(PaymentEntity.class)))
                .thenAnswer(invocation -> {
                    PaymentEntity paymentEntity = invocation.getArgument(0, PaymentEntity.class);
                    return Mono.just(paymentEntity);
                });
        Mockito.when(paymentMessageProducer.sendPaymentMessage(Mockito.any(PaymentMessage.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0, PaymentMessage.class).getId()));
        PaymentDto paymentDto = TestDataHelper.buildPaymentDto();
        PaymentEntity paymentEntity = TestDataHelper.buildPaymentEntity();
        Mockito.when(paymentMapper.paymentDtoToEntity(paymentDto)).thenReturn(paymentEntity);
        Mockito.when(paymentMapper.paymentEntityToPaymentMessage(paymentEntity))
                .thenReturn(PaymentMessage.builder().id(paymentEntity.getId()).build());

        StepVerifier.create(paymentService.createPayment(paymentDto))
                .assertNext(paymentId -> Assertions.assertEquals(paymentEntity.getId(), paymentId))
                .verifyComplete();
    }

}