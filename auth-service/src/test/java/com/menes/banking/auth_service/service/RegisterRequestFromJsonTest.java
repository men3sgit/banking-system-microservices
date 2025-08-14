package com.menes.banking.auth_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menes.banking.auth_service.controller.model.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterRequestFromJsonTest {

    private RegisterRequest baseRequest;

    @BeforeEach
    void setUp() throws Exception {
        ObjectMapper om = new ObjectMapper();
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("data/json/register_request.json")) {
            baseRequest = om.readValue(is, RegisterRequest.class);
        }
    }

    @Test
    void testValidCase() {
        // dùng object gốc
        RegisterRequest req = cloneBase();
        assertThat(req.getEmail()).isEqualTo("duong.men@example.com");
    }

    @Test
    void testInvalidEmailCase() {
        RegisterRequest req = cloneBase();
        req.setEmail("invalid_email");
        // thực hiện validate hoặc gọi service để test
        assertThat(req.getEmail()).isEqualTo("invalid_email");
    }

    @Test
    void testBlankFirstNameCase() {
        RegisterRequest req = cloneBase();
        req.setFirstName("   ");
        // test validate...
        assertThat(req.getFirstName().trim()).isEmpty();
    }

    private RegisterRequest cloneBase() {
        RegisterRequest copy = new RegisterRequest();
        copy.setUsername(baseRequest.getUsername());
        copy.setEmail(baseRequest.getEmail());
        copy.setPhoneNumber(baseRequest.getPhoneNumber());
        copy.setFirstName(baseRequest.getFirstName());
        copy.setLastName(baseRequest.getLastName());
        return copy;
    }
}
