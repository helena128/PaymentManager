package com.helena128.paymentmanager.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class CardHolderInfo {

    private String name;
    private String email;
}
