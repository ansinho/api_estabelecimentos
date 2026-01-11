package com.anderson.api_estabelecimentos.controllers.dtos;

import jakarta.validation.constraints.NotBlank;

public record TipoEstabelecimentoUpdateRequest(
        @NotBlank String descricao) {
}
