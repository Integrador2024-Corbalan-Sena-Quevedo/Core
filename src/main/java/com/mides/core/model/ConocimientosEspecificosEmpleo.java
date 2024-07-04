package com.mides.core.model;

import jakarta.persistence.*;
import lombok.Data;

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
    private Empleo empleo;


}
