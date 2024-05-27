package com.mides.core.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String CI;
    private String nombre;
    private String apellido;
    private String sexo;
    private Date fechaDeNacimiento;
    private int edad;
    private String departamento;


    @Override
    public String toString() {
        return "Candidato{" +
                "CI='" + CI + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", sexo='" + sexo + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", edad=" + edad +
                ", departamento='" + departamento + '\'' +
                '}';
    }

}
