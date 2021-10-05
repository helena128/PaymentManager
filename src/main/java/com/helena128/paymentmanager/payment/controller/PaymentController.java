package com.helena128.paymentmanager.payment.controller;

import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.helena128.paymentmanager.model.ResponseMessage.SUCCESSFUL_CREATION_MSG;

@RestController
@RequestMapping(value = "/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Publisher<ResponseEntity<String>> createPayment(@Valid @RequestBody PaymentDto paymentDto) {
        return paymentService.createPayment(paymentDto)
                .map(paymentId -> ResponseEntity.ok(SUCCESSFUL_CREATION_MSG.getValue()));
    }
}
