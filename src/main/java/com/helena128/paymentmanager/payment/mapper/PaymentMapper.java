package com.helena128.paymentmanager.payment.mapper;

import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.model.PaymentMessage;
import com.helena128.paymentmanager.payment.entity.PaymentEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    PaymentEntity paymentDtoToEntity(PaymentDto paymentDto);

    PaymentMessage paymentEntityToPaymentMessage(PaymentEntity paymentEntity);

    @AfterMapping
    default void setCreatedDateTime(@MappingTarget PaymentEntity paymentEntity) {
        paymentEntity.setCreatedDateTime(Instant.now());
    }
}
