package com.anderson.api_estabelecimentos.controllers.dtos;

import com.anderson.api_estabelecimentos.entities.TipoEstabelecimento;

public record TipoEstabelecimentoResponse(
        Long id,
        String codigo,
        String descricao) {
    public static TipoEstabelecimentoResponse fromEntity(
            TipoEstabelecimento entity) {
        return new TipoEstabelecimentoResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getDescricao());
    }
}
