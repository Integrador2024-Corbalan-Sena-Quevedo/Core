package com.mides.core.model;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mides.core.service.ExperienciaLaboral;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("Candidato")
public class Candidato extends Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String documento;
    private String tipoDocumento;
    private String nombre;
    private String apellido;
    private String sexo;
    private String identidadGenero;
    @Temporal(TemporalType.DATE) // Especifica que la columna es de tipo DATE
    private LocalDate fechaDeNacimiento;
    private String estadoCivil;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Educacion educacion;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Habilidad habilidad;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Salud salud;
    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CandidatoIdioma> candidatoIdiomas ;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    @JsonManagedReference
    private DatosAdicionalesCandidato datosAdicionalesCandidato;
    @ManyToMany
    @JoinTable(
            name = "candidato_ayudaTecnica",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "ayuda_id")
    )
    @JsonManagedReference
    private List<AyudaTecnica> ayudaTecnicas;
    @ManyToMany
    @JoinTable(
            name = "candidato_prestacion",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "prestacion_id")
    )
    @JsonManagedReference
    private List<Prestacion> prestaciones;
    @ManyToMany
    @JoinTable(
            name = "candidato_area",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    @JsonManagedReference
    private List<Area> areas;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Discapacidad discapacidad;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    @JsonManagedReference
    private DisponibilidadHoraria disponibilidadHoraria;
    @JsonManagedReference
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private ExperienciaLaboral experienciaLaboral;
    @ManyToMany
    @JoinTable(
            name = "candidato_apoyo",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "apoyo_id")
    )
    @JsonManagedReference
    private List<Apoyo> apoyos;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    @JsonManagedReference
    private EncuestaCandidato encuestaCandidato;


    @Override
    public String toString() {
        return "Candidato{" +
                "id=" + id +
                ", documento='" + documento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
