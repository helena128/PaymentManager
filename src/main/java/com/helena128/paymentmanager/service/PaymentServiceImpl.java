package com.helena128.paymentmanager.service;

import com.helena128.paymentmanager.mapper.PaymentMapper;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.repository.PaymentRepository;
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

    @Override // TODO: handle transactions
    public Mono<PaymentDto> createPayment(final PaymentDto paymentDto) {
        return Mono.just(paymentDto)
                .map(paymentMapper::paymentDtoToEntity)
                .flatMap(paymentRepository::save)
                // todo: publish to kafka
                .map(paymentMapper::paymentEntityToDto);
    }
}
