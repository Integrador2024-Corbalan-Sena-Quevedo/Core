package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
// @DiscriminatorColumn(name = "tipo_apoyo")
public  class Apoyo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;

    @ManyToMany(mappedBy = "apoyos")
    @JsonBackReference
    private List<Candidato> candidatos;

    public Apoyo(String nombre) {
        this.nombre = nombre;
    }
}
