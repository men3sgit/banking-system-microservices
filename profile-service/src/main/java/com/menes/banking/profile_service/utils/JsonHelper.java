package com.menes.banking.profile_service.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.menes.banking.profile_service.config.serialize.LocalDateDeserializer;
import com.menes.banking.profile_service.config.serialize.LocalDateSerializer;
import com.menes.banking.profile_service.config.serialize.LocalDateTimeDeserializer;
import com.menes.banking.profile_service.config.serialize.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
public class JsonHelper {

    private final ObjectMapper objectMapper;

    private static JsonHelper instance;

    public JsonHelper() {
        SimpleModule localDateTimeSerialization = new SimpleModule();
        localDateTimeSerialization.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        localDateTimeSerialization.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        localDateTimeSerialization.addSerializer(LocalDate.class, new LocalDateSerializer());
        localDateTimeSerialization.addDeserializer(LocalDate.class, new LocalDateDeserializer());

        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .addModule(localDateTimeSerialization)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build();
    }

    public static JsonHelper getInstance() {
        if (instance == null) {
            instance = new JsonHelper();
        }
        return instance;
    }

    public String writeValueAsString(Object o) {
        try {
            return this.objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: ", e);
            return null;
        }
    }

    public <T> T readValue(String jsonString, JavaType javaType) {
        try {
            return this.objectMapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}