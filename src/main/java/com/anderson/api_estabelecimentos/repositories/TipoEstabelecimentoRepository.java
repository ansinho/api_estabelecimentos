package com.anderson.api_estabelecimentos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anderson.api_estabelecimentos.entities.TipoEstabelecimento;

@Repository
public interface TipoEstabelecimentoRepository extends JpaRepository<TipoEstabelecimento, Long> {

    Optional<TipoEstabelecimento> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}
