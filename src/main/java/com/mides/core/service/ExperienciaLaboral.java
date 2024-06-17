package com.mides.core.service;

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
    @OneToOne
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    private Candidato candidato;
    @ManyToMany
    @JoinTable(
            name = "experienciaLaboral_MotivoDesempleo",
            joinColumns = @JoinColumn(name = "experienciaLaboral_id"),
            inverseJoinColumns = @JoinColumn(name = "motivoDesempleo_id")
    )
    private List<MotivoDesempleo> motivosDesempleo;

    @ManyToMany
    @JoinTable(
            name = "experienciaLaboral_GustoLaboral",
            joinColumns = @JoinColumn(name = "experienciaLaboral_id"),
            inverseJoinColumns = @JoinColumn(name = "gustoLaboral_id")
    )
    private List<GustoLaboral> gustosLaborales;
    @OneToMany(mappedBy = "experienciaLaboral", cascade = CascadeType.ALL)
    private  List<Actitud> actitudes;

}
