package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seguimiento {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    @JsonBackReference
    private Empresa empresa;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    @JsonBackReference
    private Candidato candidato;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleo_id", referencedColumnName = "id")
    @JsonBackReference
    private Empleo empleo;
    private Long tramite;
    private String documentoEmpleado;
    private String nombreEmpleado;
    private String telefonoEmpleado;
    private String emailEncargado;
    private LocalDate fechaIngresoEmpleado;
    private String nombreEncargado;
    private String localidad;
    private String operadorLaboral;
    private String discapacidades;
    @OneToMany(mappedBy = "seguimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleSeguimiento> detalleSeguimientos;
}
