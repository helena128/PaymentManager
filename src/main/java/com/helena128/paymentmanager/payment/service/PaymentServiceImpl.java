package com.helena128.paymentmanager.payment.service;

import com.helena128.paymentmanager.payment.kafka.PaymentMessageProducer;
import com.helena128.paymentmanager.payment.mapper.PaymentMapper;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentMessageProducer messageProducer;

    @Override // TODO: handle transactions
    public Mono<String> createPayment(final PaymentDto paymentDto) {
        return Mono.just(paymentDto)
                .map(paymentMapper::paymentDtoToEntity)
                .doOnNext(payment -> log.debug("Saved entity with id: {}", payment.getId()))
                .flatMap(paymentRepository::save)
                .map(paymentMapper::paymentEntityToPaymentMessage)
                .flatMap(messageProducer::sendPaymentMessage);
    }
}
