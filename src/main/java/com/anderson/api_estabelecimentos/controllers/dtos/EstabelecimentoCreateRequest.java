package com.anderson.api_estabelecimentos.controllers.dtos;

import org.hibernate.validator.constraints.br.CNPJ;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EstabelecimentoCreateRequest(
        @NotBlank @Size(max = 150) 
        @Schema(example = "Meu Bar em Brasília")
        String nome,
        
        @NotBlank
        @CNPJ(message = "CNPJ inválido")
        @Schema(example = "12345678000199")
        String cnpj,
        
        @NotNull 
        @Schema(example = "BAR")
        String codigoTipo,
        
        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0")
        @Schema(example = "-46.6333")
        double longitude,
        
        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0")
        @Schema(example = "-23.5505")
        double latitude) {
}
