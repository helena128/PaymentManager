package com.helena128.paymentmanager.repository;

import com.helena128.paymentmanager.entity.PaymentEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentRepository extends ReactiveCrudRepository<PaymentEntity, Long> {

}
