package com.helena128.paymentmanager.mapper;

import com.helena128.paymentmanager.entity.PaymentEntity;
import com.helena128.paymentmanager.model.PaymentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto paymentEntityToDto(PaymentEntity paymentEntity);

    PaymentEntity paymentDtoToEntity(PaymentDto paymentDto);

}
