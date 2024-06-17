package com.mides.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorColumn(name = "tipo_apoyo")
public abstract class Apoyo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;

    @ManyToMany(mappedBy = "apoyos")
    private List<Candidato> candidatos;

    public Apoyo(String nombre) {
        this.nombre = nombre;
    }
}
