package com.mides.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoIdioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "idioma_id",referencedColumnName = "id")
    private Idioma idioma;

    private String nivel;
}
