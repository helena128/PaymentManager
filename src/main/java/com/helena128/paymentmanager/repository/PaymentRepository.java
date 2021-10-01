package com.helena128.paymentmanager.repository;

import com.helena128.paymentmanager.entity.PaymentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface PaymentRepository extends ReactiveMongoRepository<PaymentEntity, String> {

}
