package com.anderson.api_estabelecimentos.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoCreateRequest;
import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoListagemResponse;
import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoResponse;
import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoResponse.GeoPointResponse;
import com.anderson.api_estabelecimentos.controllers.dtos.EstabelecimentoUpdateRequest;
import com.anderson.api_estabelecimentos.controllers.dtos.FeatureCollectionGeoJSON;
import com.anderson.api_estabelecimentos.controllers.dtos.FeatureGeoJSON;
import com.anderson.api_estabelecimentos.entities.Estabelecimento;
import com.anderson.api_estabelecimentos.entities.TipoEstabelecimento;
import com.anderson.api_estabelecimentos.exceptions.BusinessException;
import com.anderson.api_estabelecimentos.exceptions.ResourceNotFoundException;
import com.anderson.api_estabelecimentos.repositories.EstabelecimentoRepository;
import com.anderson.api_estabelecimentos.repositories.TipoEstabelecimentoRepository;

@Service
public class EstabelecimentoService {

        private final EstabelecimentoRepository repository;
        private final TipoEstabelecimentoRepository tipoRepository;

        public EstabelecimentoService(
                        EstabelecimentoRepository repository,
                        TipoEstabelecimentoRepository tipoRepository) {
                this.repository = repository;
                this.tipoRepository = tipoRepository;
        }

        public EstabelecimentoResponse criar(EstabelecimentoCreateRequest request) {
                repository.findByCnpj(request.cnpj())
                                .ifPresent(est -> {
                                        throw new BusinessException(
                                                        "Já existe um estabelecimento com o CNPJ informado");
                                });

                GeometryFactory geometryFactory = new GeometryFactory();
                Point geom = geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));

                Estabelecimento estabelecimento = Estabelecimento.builder()
                                .nome(request.nome())
                                .cnpj(request.cnpj())
                                .tipoEstabelecimento(buscarTipoPorCodigo(request.codigoTipo()))
                                .geom(geom)
                                .build();

                repository.save(estabelecimento);
                return EstabelecimentoResponse.fromEntity(estabelecimento);
        }

        private TipoEstabelecimento buscarTipoPorCodigo(String codigo) {
                return tipoRepository.findByCodigo(codigo)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Tipo de estabelecimento não encontrado", codigo));
        }

        public FeatureCollectionGeoJSON listarGeometria() {
                List<Estabelecimento> entidades = repository.findAll();
                List<FeatureGeoJSON> features = entidades.stream()
                                .map(entity -> new FeatureGeoJSON(
                                                "Feature",
                                                Map.of(
                                                                "id", entity.getId().toString(),
                                                                "nome", entity.getNome(),
                                                                "cnpj", entity.getCnpj(),
                                                                "tipo", Map.of(
                                                                                "id", entity.getTipoEstabelecimento().getId(),
                                                                                "codigo",
                                                                                entity.getTipoEstabelecimento().getCodigo(),
                                                                                "descricao",
                                                                                entity.getTipoEstabelecimento().getDescricao())),
                                                GeoPointResponse.fromPoint(entity.getGeom())))
                                .toList();
                return new FeatureCollectionGeoJSON("FeatureCollection", features);
        }

        public List<EstabelecimentoListagemResponse> listar() {
                return repository.findAll().stream()
                                .map(EstabelecimentoListagemResponse::fromEntity)
                                .toList();
        }

        public EstabelecimentoResponse buscarPorId(UUID id) {
                Estabelecimento estabelecimento = repository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado", id));

                return EstabelecimentoResponse.fromEntity(estabelecimento);
        }

        public EstabelecimentoResponse buscarPorCnpj(String cnpj) {
                Estabelecimento estabelecimento = repository.findByCnpj(cnpj)
                                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado",
                                                cnpj));

                return EstabelecimentoResponse.fromEntity(estabelecimento);
        }

        public EstabelecimentoResponse atualizar(
                        UUID id,
                        EstabelecimentoUpdateRequest request) {

                Estabelecimento estabelecimento = repository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado", id));

                GeometryFactory geometryFactory = new GeometryFactory();
                Point geom = geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));

                Estabelecimento.builder()
                                .nome(request.nome())
                                .tipoEstabelecimento(buscarTipoPorCodigo(request.codigoTipo()))
                                .geom(geom)
                                .build();
                repository.save(estabelecimento);

                return EstabelecimentoResponse.fromEntity(estabelecimento);
        }

        public void remover(UUID id) {
                Estabelecimento estabelecimento = repository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado", id));

                repository.delete(estabelecimento);
        }

}
