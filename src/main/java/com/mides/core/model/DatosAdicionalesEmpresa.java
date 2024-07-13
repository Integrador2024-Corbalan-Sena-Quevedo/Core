package com.mides.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class DatosAdicionalesEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String empleadosContratadosConDiscapacidad;
    private String tuvoEmpleadosConDiscapacidad;
    private int empresaSinRespuesta;
    private int empresaDesierta;
    private LocalDate fechaRespuesta;
    @OneToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;
}
