package com.helena128.paymentmanager.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Table(value = "payments")
public class PaymentEntity {

    @Id
    private BigInteger id;

    @Column(value = "cardholder_name")
    private String cardHolderName;

    @Column(value = "card_number") // TODO: type + encryption
    private String cardNumber;

    @Column(value = "expiry_date")
    private LocalDate localDate;

    @Column(value = "cvv") // TODO: type + encryption
    private String cvv;

    @Column(value = "created_datetime")
    private LocalDateTime createdDateTime;

    @Column(value = "sum")
    private BigDecimal sum;
}
