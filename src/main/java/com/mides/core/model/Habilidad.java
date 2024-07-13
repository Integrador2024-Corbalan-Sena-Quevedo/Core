package com.mides.core.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Habilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int power_point;
    private int word;
    private int excel;
    private int internet;
    private int LSU;
    private int manejo_de_dinero;
    private int autonomia_en_transporte_publico;
    private int imagen_personal;
    private String descripcion;
    private int otrasHabilidades;
    @OneToOne
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    private Candidato candidato;
}
