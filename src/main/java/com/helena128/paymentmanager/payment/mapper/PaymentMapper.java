package com.helena128.paymentmanager.payment.mapper;

import com.helena128.paymentmanager.payment.entity.PaymentEntity;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.model.PaymentMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    PaymentDto paymentEntityToDto(PaymentEntity paymentEntity);

    PaymentEntity paymentDtoToEntity(PaymentDto paymentDto);

    PaymentMessage paymentEntityToPaymentMessage(PaymentEntity paymentEntity);

}
