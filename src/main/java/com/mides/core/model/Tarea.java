package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data

@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String otras;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void setUnDetalleTarea(DetalleTarea det) {
        for(DetalleTarea unDetalle : detalleTarea){
            if(unDetalle.getId().equals(det.getId())){
                unDetalle.setDetalle(det.getDetalle());
                break;
            }
        }
    }

    public DetalleTarea getDetalleTareaById(Long idDetalle) {
        for(DetalleTarea unDetalle : detalleTarea){
            if(unDetalle.getId().equals(idDetalle)){
                return unDetalle;
            }
        }
        return null;
    }
}
