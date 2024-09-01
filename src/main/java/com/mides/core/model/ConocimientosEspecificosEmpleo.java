package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class ConocimientosEspecificosEmpleo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String computacion;
    private String otrosComputacion;
    private String idiomas;
    private String idiomaCompetenciaYrequisito;
    private String ingles;
    private String nivelIdiomasRequeridosEscrituraIngles;
    private String nivelIdiomasRequeridosHablaIngles;
    private String nivelIdiomasRequeridosLecturaIngles;
    private String portgues;
    private String nivelIdiomasRequeridosEscrituraPortugues;
    private String nivelIdiomasRequeridosHablaPortugues;
    private String nivelIdiomasRequeridosLecturaPortugues;
    private String lee;
    @OneToOne
    @JoinColumn(name = "empleo_id", referencedColumnName = "id")
    @JsonBackReference
    private Empleo empleo;
}
