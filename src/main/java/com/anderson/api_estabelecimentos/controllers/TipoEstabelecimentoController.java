package com.anderson.api_estabelecimentos.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.api_estabelecimentos.controllers.dtos.TipoEstabelecimentoCreateRequest;
import com.anderson.api_estabelecimentos.controllers.dtos.TipoEstabelecimentoResponse;
import com.anderson.api_estabelecimentos.controllers.dtos.TipoEstabelecimentoUpdateRequest;
import com.anderson.api_estabelecimentos.services.TipoEstabelecimentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tipos de Estabelecimento")
@RestController
@RequestMapping("/tipos-estabelecimento")
public class TipoEstabelecimentoController {

    private final TipoEstabelecimentoService service;

    public TipoEstabelecimentoController(
            TipoEstabelecimentoService service) {
        this.service = service;
    }

    @Operation(summary = "Criar novo tipo de estabelecimento")
    @PostMapping
    public TipoEstabelecimentoResponse criar(
            @RequestBody @Valid TipoEstabelecimentoCreateRequest request) {
        return service.criar(request);
    }

    @Operation(summary = "Listar todos os tipos de estabelecimento")
    @GetMapping
    public List<TipoEstabelecimentoResponse> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar tipo de estabelecimento por código")
    @GetMapping("/{codigo}")
    public TipoEstabelecimentoResponse buscar(
            @PathVariable String codigo) {
        return service.buscarPorCodigo(codigo);
    }

    @Operation(summary = "Atualizar tipo de estabelecimento por código")
    @PutMapping("/{codigo}")
    public TipoEstabelecimentoResponse atualizar(
            @PathVariable String codigo,
            @RequestBody @Valid TipoEstabelecimentoUpdateRequest request) {
        return service.atualizar(codigo, request);
    }

    @Operation(summary = "Remover tipo de estabelecimento por código")
    @DeleteMapping("/{codigo}")
    public void remover(@PathVariable String codigo) {
        service.remover(codigo);
    }
}
