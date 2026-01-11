package com.anderson.api_estabelecimentos.controllers.dtos;

import jakarta.validation.constraints.NotBlank;

public record TipoEstabelecimentoCreateRequest(
        @NotBlank String codigo,
        @NotBlank String descricao) {
}
