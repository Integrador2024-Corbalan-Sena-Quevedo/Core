package com.mides.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@DiscriminatorValue("Empresa")
public class Empresa extends Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String rut;
    private String nombre;
    private String actividadEconomica;
    private String ramaEconomica;
    private String personaReferencia;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Empleo> empleo;
    private String cvsEnviados;
    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL)
    private DatosAdicionalesEmpresa datosAdicionalesEmpresa;
    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL)
    private EncuestaEmpresa encuestaEmpresa;
}