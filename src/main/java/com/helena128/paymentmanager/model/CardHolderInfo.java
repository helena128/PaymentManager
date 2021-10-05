package com.helena128.paymentmanager.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardHolderInfo {

    private String name;
    private String email;
}
