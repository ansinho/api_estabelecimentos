package com.anderson.api_estabelecimentos.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoCreateRequest;
import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoListagemResponse;
import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoResponse;
import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoUpdateRequest;
import com.anderson.api_estabelecimentos.controllers.dtos.FeatureCollectionGeoJSON;
import com.anderson.api_estabelecimentos.services.EstabelecimentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Estabelecimento")
@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

    private final EstabelecimentoService service;

    public EstabelecimentoController(
            EstabelecimentoService service) {
        this.service = service;
    }

    @Operation(summary = "Criar novo estabelecimento")
    @PostMapping
    public EstabelecimentoResponse criar(
            @RequestBody @Valid EstabelecimentoCreateRequest request) {
        return service.criar(request);
    }

    @Operation(summary = "Listar feature collection geoJSON dos estabelecimentos")
    @GetMapping("/geometrias")
    public FeatureCollectionGeoJSON listarGeometrias() {
        return service.listarGeometria();
    }

    @Operation(summary = "Listar todos os estabelecimentos")
    @GetMapping
    public List<EstabelecimentoListagemResponse> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar estabelecimento por id")
    @GetMapping("/{id}")
    public EstabelecimentoResponse buscarPorId(
            @PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Buscar estabelecimento por cnpj")
    @GetMapping("/cnpj/{cnpj}")
    public EstabelecimentoResponse buscarPorCnpj(
            @PathVariable String cnpj) {
        return service.buscarPorCnpj(cnpj);
    }

    @Operation(summary = "Atualizar estabelecimento por id")
    @PutMapping("/{id}")
    public EstabelecimentoResponse atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid EstabelecimentoUpdateRequest request) {
        return service.atualizar(id, request);
    }

    @Operation(summary = "Remover estabelecimento por id")
    @DeleteMapping("/{id}")
    public void remover(@PathVariable UUID id) {
        service.remover(id);
    }
}
