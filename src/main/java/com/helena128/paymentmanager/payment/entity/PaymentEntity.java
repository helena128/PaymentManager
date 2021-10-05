package com.helena128.paymentmanager.payment.entity;

import com.helena128.paymentmanager.model.CardHolderInfo;
import com.helena128.paymentmanager.model.CardInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(value = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    private String id;

    private CardHolderInfo cardHolderInfo;

    private CardInfo cardInfo;

    private BigDecimal sum;

    private String recipient;

    private Instant createdDateTime;
}
