package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

public class DatosAdicionalesCandidato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String registoEnCNHD;
    private int conduce;

    private String tipoLibreta;
    private String enviaCV;
    private String hijos;
    private int cantHijos;
    private String grupoFamiliar;
    private String autorizacionDarDatos;
    private String infomacionPersonal;
    private String cuidados;
    @OneToOne
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    @JsonBackReference
    private Candidato candidato;

}
