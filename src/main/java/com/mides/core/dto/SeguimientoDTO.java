package com.mides.core.dto;

import com.mides.core.model.Candidato;
import com.mides.core.model.DetalleSeguimiento;
import com.mides.core.model.Empresa;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SeguimientoDTO {
    private Long id;
    private Long empresaId;
    private Long empleoId;
    private Long seguimientoId;
    private String nombreEmpresa;
    private String nombreEmpleo;
    private String operadorLaboral;
    private Long tramite;
    private String documentoEmpleado;
    private String nombreEmpleado;
    private String discapacidades;
    private String emailEncargado;
    private LocalDate fechaIngresoEmpleado;
    private String nombreEncargado;
    private String localidad;
    private List<String> detalles;
    private String telefonoEmpleado;
}
