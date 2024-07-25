package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mides.core.service.ExperienciaLaboral;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String observacionDeActiud;
    @ManyToOne
    @JoinColumn(name = "experienciaLaboral_id", referencedColumnName = "id")
    @JsonBackReference
    private ExperienciaLaboral experienciaLaboral;


    public Actitud(String nombre) {
        this.nombre = nombre;
    }
}
