package com.mides.core.service;

import com.mides.core.model.Tarea;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DetalleTarea {
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE)
     private Long id;
     private String detalle;
     @ManyToOne
     @JoinColumn(name = "tarea_id", referencedColumnName = "id")
     private Tarea tarea;

}
