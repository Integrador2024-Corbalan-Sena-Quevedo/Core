package com.mides.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;

    @OneToMany(mappedBy = "idioma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidatoIdioma> candidatoIdiomas;


    public Idioma(String nombre) {
        this.nombre = nombre;
    }
}
