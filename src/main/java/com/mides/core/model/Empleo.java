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
    private int codigo;
    private String supervisores;
    private String companeros;
    private String subordinados;
    private String edadPreferente;
    private String rangoDeEdad;
    private String formacionAcademica;
    private String libretaConducir;
    private String categoriaLibretaConducir;
    private String experienciaPrevia;
    private String tiempoDeExperienciaMinima;
    private String disponibilidadHoraria;
    private String cargaHorariaSemanal;
    private String cargaHorariaTipo;
    private String diasDeSemanaParaCubrirPuesto;
    private String diasDeSemanaParaCubrirPuestoOtro;
    private String detalleSalarial;
    private String remuneracionOfrecida;
    private String tipoRemuneracion;
    private String tipoRemuneracionOtro;
    private String categoria;
    @OneToOne(mappedBy = "empleo", cascade = CascadeType.ALL)
    private ConocimientosEspecificosEmpleo ConocimientosEspecificosEmpleo;
    @ManyToOne
    @JoinColumn(name = "empresaId", referencedColumnName = "id")
    private Empresa empresa;


}
