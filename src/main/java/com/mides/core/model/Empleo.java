package com.mides.core.model;

import com.fasterxml.jackson.annotation.*;
import com.mides.core.enums.NivelEducativo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Empleo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombrePuesto;
    private int nroPuestosDisponible;
    private String contratoATermino;
    private String plazoContrato;
    private String departamento;
    private String localidades;
    @OneToMany(mappedBy = "empleo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Tarea> tareas;
    private String trabajoAlExterior;
    private String implicaDesplazamientos;
    private String ritmoImpuesto;
    private int codigo;
    private String supervisores;
    private String companeros;
    private String subordinados;
    private String edadPreferente;
    private String rangoDeEdad;
    private String formacionAcademica;
    private int formacionNumerico;
    private String libretaConducir;
    private String categoriaLibretaConducir;
    private String experienciaPrevia;
    private String tiempoDeExperienciaMinima;
    private String disponibilidadHoraria;
    private String cargaHorariaSemanal;
    private String cargaHorariaTipo;
    private String diasDeSemanaParaCubrirPuesto;
    private String diasDeSemanaParaCubrirPuestoOtro;
    private String detalleSalarial;
    private String remuneracionOfrecida;
    private String tipoRemuneracion;
    private String tipoRemuneracionOtro;
    private String categoria;
    @OneToOne(mappedBy = "empleo", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ConocimientosEspecificosEmpleo conocimientosEspecificosEmpleo;
    @ManyToOne
    @JoinColumn(name = "empresaId", referencedColumnName = "id")
    @JsonBackReference
    private Empresa empresa;
    private int activo;
    @OneToMany
    @JsonManagedReference
    private List<Seguimiento> seguimientos;


    public void setUnaTarea(Tarea tarea) {
        for (Tarea t : tareas) {
            if (t.getId().equals(tarea.getId())) {

                tareas.remove(t);
                tareas.add(tarea);
                break;
            }
        }
    }

    public void setFormacionAcademicaEnum(String nivelEducativo) {
        this.formacionAcademica = nivelEducativo;
        this.formacionNumerico = NivelEducativo.valueOf(nivelEducativo.toUpperCase().replace(" ", "_")).getNivel();
    }
}
