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
public class Discapacidad {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String diagnostico;
    @OneToOne
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    private Candidato candidato;
    @ManyToMany
    @JoinTable(
            name = "discapacidad_tipoDiscapacidad",
            joinColumns = @JoinColumn(name = "discapacidad_id"),
            inverseJoinColumns = @JoinColumn(name = "tipoDiscapacidad_id")
    )
    private List<TipoDiscapacidad> tipoDiscapacidades;


}
