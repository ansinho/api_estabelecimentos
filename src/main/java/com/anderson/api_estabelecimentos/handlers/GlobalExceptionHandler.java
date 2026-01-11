package com.anderson.api_estabelecimentos.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anderson.api_estabelecimentos.exceptions.BusinessException;
import com.anderson.api_estabelecimentos.exceptions.ConflictException;
import com.anderson.api_estabelecimentos.exceptions.ErrorResponseDTO;
import com.anderson.api_estabelecimentos.exceptions.ResourceNotFoundException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ErrorResponseDTO> handleBusiness(
                        BusinessException ex,
                        HttpServletRequest request) {
                return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponseDTO> handleNotFound(
                        ResourceNotFoundException ex,
                        HttpServletRequest request) {
                return build(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        }

        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<ErrorResponseDTO> handleConflict(
                        ConflictException ex,
                        HttpServletRequest request) {
                return build(HttpStatus.CONFLICT, ex.getMessage(), request);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDTO> handleGeneric(
                        Exception ex,
                        HttpServletRequest request) throws Exception {

                String path = request.getRequestURI();
                 ex.printStackTrace(); // TEMPORÁRIO

                if (path.startsWith("/v3/api-docs")
                                || path.startsWith("/swagger")
                                || path.startsWith("/swagger-ui")) {
                        throw ex;
                }

                return build(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Erro interno inesperado",
                                request);
        }

        private ResponseEntity<ErrorResponseDTO> build(
                        HttpStatus status,
                        String message,
                        HttpServletRequest request) {
                return ResponseEntity.status(status).body(
                                new ErrorResponseDTO(
                                                Instant.now(),
                                                status.value(),
                                                status.getReasonPhrase(),
                                                message,
                                                request.getRequestURI()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponseDTO> handleValidation(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {
                String message = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(FieldError::getDefaultMessage)
                                .findFirst()
                                .orElse("Erro de validação");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                new ErrorResponseDTO(
                                                Instant.now(),
                                                HttpStatus.BAD_REQUEST.value(),
                                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                                message,
                                                request.getRequestURI()));
        }

}
