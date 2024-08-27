package com.mides.core.model;

import com.mides.core.model.DetalleTarea;
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