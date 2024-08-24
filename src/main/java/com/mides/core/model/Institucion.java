package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Institucion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Instituciondeseo tipo;

    public enum Instituciondeseo{
        EDUCATIVA,
        RECREATIVA,
        REHABILITACION,
        OTRA;
    }




    public Institucion(Instituciondeseo tipo) {
        this.tipo = tipo;
    }

    @ManyToMany(mappedBy = "institucionesDeseo")
    @JsonBackReference
    private List<Educacion> educaciones;

}
