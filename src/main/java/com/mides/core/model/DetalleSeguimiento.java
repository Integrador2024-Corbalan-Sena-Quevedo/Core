package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleSeguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String detalle;
    private LocalDate fecha;
    private String usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seguimiento_id")
    private Seguimiento seguimiento;

    public DetalleSeguimiento(String detalle, LocalDate fecha) {
        this.detalle = detalle;
        this.fecha = fecha;
    }

    public DetalleSeguimiento(String detalle, LocalDate fecha, String usuario, Seguimiento seguimiento) {
        this.detalle = detalle;
        this.fecha = fecha;
        this.usuario = usuario;
        this.seguimiento = seguimiento;
    }
}
