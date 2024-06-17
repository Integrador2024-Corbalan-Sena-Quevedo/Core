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
public class Turno {

    public enum turnoDisponible{
        MANIANA,
        TARDE,
        NOCHE,
        INDIFERENTE,
        ROTATIVO
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private turnoDisponible turno;

    @ManyToMany(mappedBy = "turnos")
    private List<DisponibilidadHoraria> disponibilidadHorarias;

    public Turno(turnoDisponible turno) {
        this.turno = turno;
    }
}
