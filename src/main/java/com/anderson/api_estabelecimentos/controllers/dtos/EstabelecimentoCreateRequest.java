package com.anderson.api_estabelecimentos.controllers.dtos;

import org.hibernate.validator.constraints.br.CNPJ;
import org.locationtech.jts.geom.Point;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EstabelecimentoCreateRequest(

        @NotBlank @Size(max = 150) String nome,

        @NotBlank @Size(min = 14, max = 14) @CNPJ(message = "CNPJ inválido") String cnpj,

        @NotNull Long tipoId,

        @NotNull Point geom) {

}
