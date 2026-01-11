package com.anderson.api_estabelecimentos.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoCreateRequest;
import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoResponse;
import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoUpdateRequest;
import com.anderson.api_estabelecimentos.entities.Estabelecimento;
import com.anderson.api_estabelecimentos.exceptions.BusinessException;
import com.anderson.api_estabelecimentos.exceptions.ResourceNotFoundException;
import com.anderson.api_estabelecimentos.repositories.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {

    private final EstabelecimentoRepository repository;

    public EstabelecimentoService(EstabelecimentoRepository repository) {
        this.repository = repository;
    }

    public EstabelecimentoResponse criar(
            EstabelecimentoCreateRequest request) {

        repository.findByCnpj(request.cnpj())
                .ifPresent(estabelecimento -> {
                    throw new BusinessException("Já existe um estabelecimento com o CNPJ informado");
                });

        Estabelecimento estabelecimento = Estabelecimento.builder()
                .nome(request.nome())
                .cnpj(request.cnpj())
                .geom(request.geom())
                .build();

        repository.save(estabelecimento);

        return EstabelecimentoResponse.fromEntity(estabelecimento);
    }

    public List<EstabelecimentoResponse> listar() {
        return repository.findAll().stream()
                .map(EstabelecimentoResponse::fromEntity)
                .toList();
    }

    public EstabelecimentoResponse buscarPorId(UUID id) {
        Estabelecimento estabelecimento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado", id));

        return EstabelecimentoResponse.fromEntity(estabelecimento);
    }

    public EstabelecimentoResponse buscarPorCnpj(String cnpj) {
        Estabelecimento estabelecimento = repository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado", cnpj));

        return EstabelecimentoResponse.fromEntity(estabelecimento);
    }

    public EstabelecimentoResponse atualizar(
            UUID id,
            EstabelecimentoUpdateRequest request) {

        Estabelecimento estabelecimento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado", id));

        estabelecimento.atualizar(request.nome(), request.tipoId(), request.geom());
        repository.save(estabelecimento);

        return EstabelecimentoResponse.fromEntity(estabelecimento);
    }

    public void remover(UUID id) {
        Estabelecimento estabelecimento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado", id));

        repository.delete(estabelecimento);
    }

}
