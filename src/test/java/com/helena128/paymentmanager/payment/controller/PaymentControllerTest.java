package com.helena128.paymentmanager.payment.controller;

import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.model.PaymentMessage;
import com.helena128.paymentmanager.payment.entity.PaymentEntity;
import com.helena128.paymentmanager.payment.kafka.PaymentMessageProducer;
import com.helena128.paymentmanager.payment.repository.PaymentRepository;
import com.helena128.paymentmanager.payment.util.TestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PaymentController.class, properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
@ContextConfiguration(classes = PaymentControllerTest.PaymentControllerTestConfig.class)
public class PaymentControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private PaymentMessageProducer paymentMessageProducer;

    @BeforeEach
    public void setup() {
        Mockito.when(paymentRepository.save(Mockito.any(PaymentEntity.class)))
                .thenAnswer(invocation -> {
                    PaymentEntity paymentEntity = invocation.getArgument(0, PaymentEntity.class);
                    paymentEntity.setId(UUID.randomUUID().toString());
                    return Mono.just(paymentEntity);
                });
        Mockito.when(paymentMessageProducer.sendPaymentMessage(Mockito.any(PaymentMessage.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0, PaymentMessage.class).getId()));
    }

    @Test
    public void testPostingCorrectData() {
        PaymentDto paymentDto = TestDataHelper.buildPaymentDto();
        webClient.post()
                .uri("/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(paymentDto))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testPostingIncorrectData() {
        PaymentDto paymentDto = TestDataHelper.buildPaymentDto();
        paymentDto.getCardInfo().setCardNumber("12345678");
        webClient.post()
                .uri("/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(paymentDto))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void testPostingNullData() {
        PaymentDto paymentDto = TestDataHelper.buildPaymentDto();
        paymentDto.setCardInfo(null);
        webClient.post()
                .uri("/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(paymentDto))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Configuration
    @ComponentScan(basePackages = {"com.helena128.paymentmanager.payment.controller",
            "com.helena128.paymentmanager.payment.crypto",
            "com.helena128.paymentmanager.payment.exception",
            "com.helena128.paymentmanager.payment.mapper",
            "com.helena128.paymentmanager.payment.service",
            "com.helena128.paymentmanager.payment.validator"})
    public static class PaymentControllerTestConfig {

    }
}