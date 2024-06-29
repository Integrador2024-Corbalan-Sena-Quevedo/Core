package com.mides.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Empleo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombrePuesto;
    private int nroPuestosDisponible;
    private String contratoATermino;
    private String plazoContrato;
    private String departamento;
    private String localidades;
    @OneToMany(mappedBy = "empleo", cascade = CascadeType.ALL)
    private List<Tarea> tareas;
    private String trabajoAlExterior;
    private String implicaDesplazamientos;
    private String ritmoImpuesto;
    @ManyToOne
    @JoinColumn(name = "empresaId", referencedColumnName = "id")
    private Empresa empresa;


}
