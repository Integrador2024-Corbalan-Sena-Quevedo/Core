package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mides.core.service.DetalleTarea;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String otras;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DetalleTarea> detalleTarea;
    @ManyToOne
    @JoinColumn(name = "empleo_id")
    @JsonBackReference
    private Empleo empleo;

    public Tarea(String nombre, List<DetalleTarea> detalles) {
        this.nombre = nombre;
        this.detalleTarea = detalles;
    }

    public Tarea() {
    }
}
