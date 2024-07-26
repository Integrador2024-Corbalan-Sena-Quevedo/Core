package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class EncuestaEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long idEncuesta;
    private LocalDate fechaDeCreacion;
    private String observaciones;
    private String comentarios;
    private String calificacionEncuesta;
    @OneToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    @JsonBackReference
    private Empresa empresa;
}
