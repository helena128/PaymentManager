package com.helena128.paymentmanager.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode
public class CardHolderInfo {

    @NotNull
    private String name;
    @NotNull
    private String email;
}
