package com.anderson.api_estabelecimentos.controllers.dtos;

import java.util.UUID;

import org.locationtech.jts.geom.Point;

import com.anderson.api_estabelecimentos.entities.Estabelecimento;

public record EstabelecimentoResponse(
        UUID id,
        String nome,
        String cnpj,
        TipoEstabelecimentoResponse tipo,
        GeoPointResponse geom) {

    public static EstabelecimentoResponse fromEntity(Estabelecimento entity) {
        return new EstabelecimentoResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCnpj(),
                TipoEstabelecimentoResponse.fromEntity(entity.getTipo()),
                GeoPointResponse.fromPoint(entity.getGeom()));
    }

    public record GeoPointResponse(
            String type,
            double[] coordinates) {
        public static GeoPointResponse fromPoint(Point point) {
            return new GeoPointResponse(
                    "Point",
                    new double[] { point.getX(), point.getY() });
        }
    }
}
