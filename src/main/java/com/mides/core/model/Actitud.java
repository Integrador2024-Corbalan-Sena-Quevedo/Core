package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Actitud {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;

    @ManyToMany(mappedBy = "actitudes")
    @JsonBackReference
    private List<ExperienciaLaboral> experienciaLaboral;


    public Actitud(String nombre) {
        this.nombre = nombre;
    }
}
