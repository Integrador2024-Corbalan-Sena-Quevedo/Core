package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Candidato candidato;
    @ManyToMany
    @JoinTable(
            name = "discapacidad_tipoDiscapacidad",
            joinColumns = @JoinColumn(name = "discapacidad_id"),
            inverseJoinColumns = @JoinColumn(name = "tipoDiscapacidad_id")
    )
    @JsonManagedReference
    private List<TipoDiscapacidad> tipoDiscapacidades;


}
