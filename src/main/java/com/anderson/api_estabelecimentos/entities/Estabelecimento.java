package com.anderson.api_estabelecimentos.entities;

import java.util.UUID;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_estabelecimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_codigo")
    private TipoEstabelecimento tipoCodigo;

    @Column(nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point geom;

    public void atualizar(String nome, String tipoCodigo, Point geom) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
        if (tipoCodigo != null) {
            this.tipoCodigo = TipoEstabelecimento.builder().codigo(tipoCodigo).build();
        }
        if (geom != null) {
            this.geom = geom;
        }
    }

}
