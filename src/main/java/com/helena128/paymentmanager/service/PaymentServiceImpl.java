package com.helena128.paymentmanager.service;

import com.helena128.paymentmanager.entity.PaymentEntity;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @PostConstruct
    public void init() {
        paymentRepository.save(PaymentEntity.builder()
                .cardHolderName("Jane Ivanova")
                .build()).block();
    }

    @Override
    public PaymentDto createPayment(final PaymentDto paymentDto) {
        return null;
    }
}
