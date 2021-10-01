package com.helena128.paymentmanager.service;

import com.helena128.paymentmanager.model.PaymentDto;
import reactor.core.publisher.Mono;

public interface PaymentService {

    Mono<String> createPayment(PaymentDto paymentDto);
}
