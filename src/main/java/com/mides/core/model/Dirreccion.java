package com.mides.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dirreccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String departamento;
    private String localidad;
    private String calle;
    private int calleIncluida;
    private int numeroPuerta;
    private String apartamento;
    private String esquinaUno;
    private String esquinaDos;
    private String kilometro;
    private String observacionesDireccion;
    @OneToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;
}
