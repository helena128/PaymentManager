package com.helena128.paymentmanager.payment.repository;

import com.helena128.paymentmanager.payment.entity.PaymentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends ReactiveMongoRepository<PaymentEntity, String> {

}
