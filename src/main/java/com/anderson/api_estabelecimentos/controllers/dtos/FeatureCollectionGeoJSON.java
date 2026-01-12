package com.anderson.api_estabelecimentos.controllers.dtos;

import java.util.List;

public record FeatureCollectionGeoJSON(
        String type,
        List<FeatureGeoJSON> features) {
}
