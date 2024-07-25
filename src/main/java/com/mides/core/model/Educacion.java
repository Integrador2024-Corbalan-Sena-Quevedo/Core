package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Educacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String situacionActual;
    private String nivelEducativo;
    private int aniosEducacion;
    private String nombreInstitucion;
    private String participacionInstitucion;
    @ManyToMany
    @JoinTable(
            name = "educacion_institucion",
            joinColumns = @JoinColumn(name = "educacion_id"),
            inverseJoinColumns = @JoinColumn(name = "institucion_id")
    )
    @JsonManagedReference
    private List<Institucion> institucionesDeseo;
    @OneToOne
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    @JsonBackReference
    private Candidato candidato;
    private String educacionNoFormal;
    private String razonDejaEstudios;
    private String deseoDeOtrasInstituciones;
    private String deseaParticiparEnAlgunaInstitucion;

}
