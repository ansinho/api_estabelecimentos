package com.anderson.api_estabelecimentos.controllers.dtos;

import java.util.Map;

import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoResponse.GeoPointResponse;

public record FeatureGeoJSON(
        String type,
        Map<String, Object> properties,
        GeoPointResponse geometry) {
}
