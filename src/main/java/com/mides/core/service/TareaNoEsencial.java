package com.mides.core.service;

import com.mides.core.model.Tarea;
import jakarta.persistence.Entity;
import java.util.List;

@Entity
public class TareaNoEsencial extends Tarea {
    public TareaNoEsencial(String nombre, List<DetalleTarea> detalles) {
        super(nombre, detalles);
    }

    public TareaNoEsencial() {
        super();
    }
}