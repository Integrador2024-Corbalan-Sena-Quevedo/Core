package com.mides.core.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@DiscriminatorValue("Sensorial")
public class Sensorial extends Apoyo{
    private  String descripcion;

    public Sensorial(String nombre) {
        super(nombre);
    }
}
