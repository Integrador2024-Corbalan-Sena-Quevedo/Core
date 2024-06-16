package com.mides.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DisponibilidadHoraria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String horasSemanales;

    @ManyToMany
    @JoinTable(
            name = "disponibilidadHoraria_turno",
            joinColumns = @JoinColumn(name = "disponibilidadHoraria_id"),
            inverseJoinColumns = @JoinColumn(name = "turno_id")
    )
    private List<Turno> turnos;
    private String diasDeLaSemana;
    private String otroDepartamento;
    @OneToOne
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    private Candidato candidato;


}
