package com.helena128.paymentmanager.controller;

import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.helena128.paymentmanager.model.ResponseMessage.SUCCESSFUL_CREATION_MSG;

@RestController
@RequestMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Publisher<ResponseEntity<String>> createPayment(@RequestBody PaymentDto paymentDto) {
        return paymentService.createPayment(paymentDto)
                .map(payment -> ResponseEntity.ok(SUCCESSFUL_CREATION_MSG.getValue()));
    }
}
