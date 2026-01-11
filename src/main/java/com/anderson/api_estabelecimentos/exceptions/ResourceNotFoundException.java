package com.anderson.api_estabelecimentos.exceptions;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(String message, Object id) {
        super(message + ". Identificador: " + id);
    }
}
