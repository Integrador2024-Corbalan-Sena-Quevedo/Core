package com.mides.core.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("Empresa")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Empresa extends Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String rut;
    private String nombre;
    private String actividadEconomica;
    private String ramaEconomica;
    private String personaReferencia;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Empleo> empleo;
    private String cvsEnviados;
    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonManagedReference
    private DatosAdicionalesEmpresa datosAdicionalesEmpresa;
    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonManagedReference
    private EncuestaEmpresa encuestaEmpresa;
    @OneToMany
    private List<Seguimiento> seguimientos;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AuditoriaEmpresa> auditoriaEmpresas;

    public Empleo getUnEmpleo(Long id){
        for (Empleo emp : empleo) {
            if (emp.getId().equals(id)) {
                return emp;
            }
        }
        return null;
    }

    public void setUnEmpleo(Empleo emp) {
        for (Empleo unEmp : empleo){
            if (unEmp.getId().equals(emp.getId())) {
                empleo.remove(unEmp);
                empleo.add(emp);
                break;
            }
        }
    }
}
