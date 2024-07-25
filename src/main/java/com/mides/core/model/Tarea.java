package com.mides.core.model;

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
    private List<DetalleTarea> detalleTarea;
    @ManyToOne
    @JoinColumn(name = "empleo_id")
    private Empleo empleo;

    public Tarea(String nombre, List<DetalleTarea> detalles) {
        this.nombre = nombre;
        this.detalleTarea = detalles;
    }

    public Tarea() {
    }
}
