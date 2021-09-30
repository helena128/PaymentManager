package com.helena128.paymentmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@Table(value = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    private BigInteger id;

    @Column(value = "cardholder_name")
    private String cardHolderName;

    @Column(value = "card_number") // TODO: type + encryption
    private String cardNumber;

    @Column(value = "expiry_date")
    private LocalDate expiryDate;

    @Column(value = "cvv") // TODO: type + encryption
    private String cvv;

    @Column(value = "created_datetime")
    private Instant createdDateTime;

    @Column(value = "sum")
    private BigDecimal sum;
}
