package com.mides.core.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("Empresa")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @JsonManagedReference
    private List<Empleo> empleo;
    private String cvsEnviados;
    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonManagedReference
    private DatosAdicionalesEmpresa datosAdicionalesEmpresa;
    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonManagedReference
    private EncuestaEmpresa encuestaEmpresa;
}
