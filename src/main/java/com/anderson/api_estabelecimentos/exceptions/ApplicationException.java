package com.anderson.api_estabelecimentos.exceptions;

public abstract class ApplicationException extends RuntimeException {

    protected ApplicationException(String message) {
        super(message);
    }
}