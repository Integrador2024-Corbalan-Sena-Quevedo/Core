package com.mides.core.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@DiscriminatorValue("Fisico")
public class Fisico extends Apoyo{

    private String descripcion;

    public Fisico(String nombre) {
        super(nombre);
    }

    public Fisico() {
        super();
    }
}
