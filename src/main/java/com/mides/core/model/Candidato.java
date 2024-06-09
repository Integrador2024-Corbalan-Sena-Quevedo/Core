package com.mides.core.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String documento;
    private String tipoDocumento;
    private String nombre;
    private String apellido;
    private String sexo;
    private Date fechaDeNacimiento;
    private int edad;
    private String estadoCivil;
    private String email;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private Dirreccion dirreccion;
    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL)
    private List<Telefono> telefonos;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private Educacion educacion;

}
