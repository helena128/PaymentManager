package com.helena128.paymentmanager.model;

public enum ResponseMessage {
    SUCCESSFUL_CREATION_MSG("Successfully created information");

    private final String value;

    ResponseMessage(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
