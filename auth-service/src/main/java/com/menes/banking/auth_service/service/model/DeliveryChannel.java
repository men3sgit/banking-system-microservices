package com.menes.banking.auth_service.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DeliveryChannel {
    SMS("sms"),
    EMAIL("email"),
    PUSH("push");

    private final String value;

    DeliveryChannel(String value) { this.value = value; }

    @JsonValue
    public String getValue() { return value; }

    @JsonCreator
    public static DeliveryChannel from(String v) {
        if (v == null) return null;
        String s = v.trim().toLowerCase();
        for (DeliveryChannel c : values()) {
            if (c.value.equals(s)) return c;
        }
        throw new IllegalArgumentException("Unsupported delivery channel: " + v);
    }
}
