package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mides.core.model.Actitud;
import com.mides.core.model.Candidato;
import com.mides.core.model.GustoLaboral;
import com.mides.core.model.MotivoDesempleo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExperienciaLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String trabajoAlgunaVez;
    private String situacionLaboral;
    private String experienciaLaboral;
    private String descripcionSituacionLaboral;
    private String ultimoPuesto;
    private String tipoTrabajo;
    private String puestoActual;
    private String inicioTrabajo;
    private String finTrabajo;
    private String tareas;
    private String tipoDeTrabajoOtros;
    private String observacionDeActiud;
    @OneToOne
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    @JsonBackReference
    private Candidato candidato;
    @ManyToMany
    @JoinTable(
            name = "experienciaLaboral_MotivoDesempleo",
            joinColumns = @JoinColumn(name = "experienciaLaboral_id"),
            inverseJoinColumns = @JoinColumn(name = "motivoDesempleo_id")
    )
    @JsonManagedReference
    private List<MotivoDesempleo> motivosDesempleo;

    @ManyToMany
    @JoinTable(
            name = "experienciaLaboral_GustoLaboral",
            joinColumns = @JoinColumn(name = "experienciaLaboral_id"),
            inverseJoinColumns = @JoinColumn(name = "gustoLaboral_id")
    )
    @JsonManagedReference
    private List<GustoLaboral> gustosLaborales;

    @ManyToMany
    @JoinTable(
            name = "experienciaLaboral_Actitud",
            joinColumns = @JoinColumn(name = "experienciaLaboral_id"),
            inverseJoinColumns = @JoinColumn(name = "actitud_id")
    )
    @JsonManagedReference
    private  List<Actitud> actitudes;

}
