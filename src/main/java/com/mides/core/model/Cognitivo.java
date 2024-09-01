package com.mides.core.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@DiscriminatorValue("Cognitivo")
public class Cognitivo extends Apoyo{

    private String descripcion;

    public Cognitivo(String nombre) {
        super(nombre);
    }

    public Cognitivo() {
        super();
    }
}
