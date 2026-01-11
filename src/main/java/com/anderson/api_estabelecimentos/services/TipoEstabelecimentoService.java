package com.anderson.api_estabelecimentos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anderson.api_estabelecimentos.controllers.dtos.TipoEstabelecimentoCreateRequest;
import com.anderson.api_estabelecimentos.controllers.dtos.TipoEstabelecimentoResponse;
import com.anderson.api_estabelecimentos.controllers.dtos.TipoEstabelecimentoUpdateRequest;
import com.anderson.api_estabelecimentos.entities.TipoEstabelecimento;
import com.anderson.api_estabelecimentos.exceptions.BusinessException;
import com.anderson.api_estabelecimentos.exceptions.ResourceNotFoundException;
import com.anderson.api_estabelecimentos.repositories.TipoEstabelecimentoRepository;

@Service
public class TipoEstabelecimentoService {

    private final TipoEstabelecimentoRepository repository;

    public TipoEstabelecimentoService(
            TipoEstabelecimentoRepository repository) {
        this.repository = repository;
    }

    public TipoEstabelecimentoResponse criar(
            TipoEstabelecimentoCreateRequest request) {

        if (repository.existsByCodigo(request.codigo())) {
            throw new BusinessException("Já existe um tipo com o código informado");
        }

        TipoEstabelecimento tipo = TipoEstabelecimento.builder()
                .codigo(request.codigo().toUpperCase())
                .descricao(request.descricao())
                .build();

        repository.save(tipo);

        return TipoEstabelecimentoResponse.fromEntity(tipo);
    }

    public List<TipoEstabelecimentoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(TipoEstabelecimentoResponse::fromEntity)
                .toList();
    }

    public TipoEstabelecimentoResponse buscarPorCodigo(String codigo) {
        TipoEstabelecimento tipo = repository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado", codigo));

        return TipoEstabelecimentoResponse.fromEntity(tipo);
    }

    public TipoEstabelecimentoResponse atualizar(
            String codigo,
            TipoEstabelecimentoUpdateRequest request) {

        TipoEstabelecimento tipo = repository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado", codigo));

        tipo.atualizarDescricao(request.descricao());
        repository.save(tipo);

        return TipoEstabelecimentoResponse.fromEntity(tipo);
    }

    public void remover(String codigo) {
        TipoEstabelecimento tipo = repository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado", codigo));

        repository.delete(tipo);
    }
}
