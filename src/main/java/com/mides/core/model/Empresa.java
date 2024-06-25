package com.mides.core.model;

import java.time.LocalDate;
import java.util.List;

public class Empresa {

    private Long id;
    private String rut;
    private String nombre;
    private String actividadEconomica;
    private ContactoEmpresa contactoEmpresa;
    private Dirreccion dirreccion;
    private String pesonasConDiscapaciadContratada;
    private int nroPuestosDisponible;
    private List<Empleo> empleo;




}
