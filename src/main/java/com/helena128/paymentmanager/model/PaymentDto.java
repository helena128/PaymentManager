package com.helena128.paymentmanager.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentDto {

    private String id;
    @NotNull
    @Valid
    private CardHolderInfo cardHolderInfo;
    @NotNull
    @Valid
    private CardInfo cardInfo;
    @NotNull
    private String recipient;
    @NotNull
    private BigDecimal sum;
}
