package com.anderson.api_estabelecimentos.controllers.dtos;

import org.locationtech.jts.geom.Point;

import jakarta.validation.constraints.Size;

public record EstabelecimentoUpdateRequest(

        @Size(max = 150) String nome,

        Long tipoId,

        Point geom) {

}