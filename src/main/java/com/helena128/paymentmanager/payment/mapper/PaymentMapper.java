package com.helena128.paymentmanager.payment.mapper;

import com.helena128.paymentmanager.model.CardInfo;
import com.helena128.paymentmanager.model.PaymentDto;
import com.helena128.paymentmanager.model.PaymentMessage;
import com.helena128.paymentmanager.payment.crypto.CryptoService;
import com.helena128.paymentmanager.payment.entity.PaymentEntity;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PaymentMapper {

    protected static final String CARD_TEMPLATE = "**** **** **** ";

    @Autowired
    protected CryptoService cryptoService;

    public abstract PaymentEntity paymentDtoToEntity(PaymentDto paymentDto);

    @Mapping(source = "cardInfo.cardNumber", target = "cardNumber")
    public abstract PaymentMessage paymentEntityToPaymentMessage(PaymentEntity paymentEntity);

    @AfterMapping
    protected void postProcessPaymentEntity(@MappingTarget PaymentEntity paymentEntity) {
        paymentEntity.setCreatedDateTime(Instant.now());
        if (Objects.nonNull(paymentEntity.getCardInfo())) {
            paymentEntity.setCardInfo(CardInfo.builder()
                    .cardNumber(cryptoService.encrypt(paymentEntity.getCardInfo().getCardNumber()))
                    .cvc(cryptoService.encrypt(paymentEntity.getCardInfo().getCardNumber()))
                    .expiryDate(cryptoService.encrypt(paymentEntity.getCardInfo().getExpiryDate()))
                    .build());
        }
    }

    @AfterMapping
    protected void postProcessPaymentMessage(@MappingTarget PaymentMessage paymentMessage) {
        if (Objects.nonNull(paymentMessage.getCardNumber()) && paymentMessage.getCardNumber().length() > 4) {
            String cardNumber = cryptoService.decrypt(paymentMessage.getCardNumber());
            cardNumber = cardNumber.substring(cardNumber.length() - 4);
            paymentMessage.setCardNumber(CARD_TEMPLATE + cardNumber);
        }
    }
}
