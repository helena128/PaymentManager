package com.helena128.paymentmanager.payment.service;

import com.helena128.paymentmanager.payment.kafka.PaymentMessageProducer;
import com.helena128.paymentmanager.payment.mapper.PaymentMapper;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.repository.PaymentRepository;
import com.helena128.paymentmanager.payment.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentMessageProducer messageProducer;
    private final List<PaymentValidator> paymentValidatorList;

    @Override
    public Mono<String> createPayment(final PaymentDto paymentDto) {
        return Mono.just(paymentDto)
                .map(this::validate)
                .map(paymentMapper::paymentDtoToEntity)
                .doOnNext(payment -> log.debug("Saved entity with id: {}", payment.getId()))
                .flatMap(paymentRepository::save)
                .map(paymentMapper::paymentEntityToPaymentMessage)
                .flatMap(messageProducer::sendPaymentMessage);
    }

    private PaymentDto validate(final PaymentDto paymentDto) {
        paymentValidatorList.forEach(validator -> validator.validate(paymentDto));
        return paymentDto;
    }
}
