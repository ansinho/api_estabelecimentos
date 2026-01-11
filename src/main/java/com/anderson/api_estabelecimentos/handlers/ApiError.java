package com.anderson.api_estabelecimentos.handlers;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime timestamp;

    public ApiError(String message) {
        this.message = message;
        this.timestamp = OffsetDateTime.now();
    }
}
