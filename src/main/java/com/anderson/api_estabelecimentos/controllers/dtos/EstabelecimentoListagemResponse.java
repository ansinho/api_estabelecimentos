package com.anderson.api_estabelecimentos.controllers.dtos;

import java.util.UUID;

import com.anderson.api_estabelecimentos.entities.Estabelecimento;

public record EstabelecimentoListagemResponse(
        UUID id,
        String nome,
        String cnpj,
        TipoEstabelecimentoResponse tipo) {

    public static EstabelecimentoListagemResponse fromEntity(Estabelecimento entity) {
        return new EstabelecimentoListagemResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCnpj(),
                TipoEstabelecimentoResponse.fromEntity(entity.getTipoCodigo()));
    }
}
