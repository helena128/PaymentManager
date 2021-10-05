package com.helena128.paymentmanager.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentDto {

    private String id;
    @NotNull
    private CardHolderInfo cardHolderInfo;
    @NotNull
    private CardInfo cardInfo;
    @NotNull
    private String recipient;
    @NotNull
    private BigDecimal sum;
}
