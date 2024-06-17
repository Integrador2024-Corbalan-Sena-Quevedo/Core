package com.mides.core.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AyudaTecnica {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    private Long id;
    @Column(unique = true)
    private String nombre;
    @ManyToMany(mappedBy = "ayudaTecnicas")
    private List<Candidato> candidatos;

    public AyudaTecnica(String nombre) {
        this.nombre = nombre;
    }
}