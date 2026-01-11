package com.anderson.api_estabelecimentos.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anderson.api_estabelecimentos.entities.Estabelecimento;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, UUID> {

    boolean existsByCnpj(String cnpj);
    
    Optional<Estabelecimento> findByCnpj(String cnpj);

}
