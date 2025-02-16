package com.mides.core.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE) // Especifica que la columna es de tipo DATE
    private LocalDate fecha_de_nacimiento;
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
    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<AuditoriaCandidato> auditoriaCandidatos; ;
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
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    @JsonManagedReference
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
    @Column(columnDefinition = "TEXT")
    private String csvBase64;
    @JsonManagedReference
    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seguimiento> seguimientos;

    @Override
    public String toString() {
        return "Candidato{" +
                "id=" + id +
                ", documento='" + documento + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", sexo='" + sexo + '\'' +
                ", identidadGenero='" + identidadGenero + '\'' +
                ", fechaDeNacimiento=" + fecha_de_nacimiento +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", emails=" + this.getEmails() +
                ", direccion=" + this.getDireccion() +
                ", telefonos=" + this.getTelefonos() +
                ", educacion=" + educacion +
                ", habilidad=" + habilidad +
                ", salud=" + salud +
                ", candidatoIdiomas=" + candidatoIdiomas +
                ", datosAdicionalesCandidato=" + datosAdicionalesCandidato +
                ", ayudaTecnicas=" + ayudaTecnicas +
                ", prestaciones=" + prestaciones +
                ", areas=" + areas +
                ", discapacidad=" + discapacidad +
                ", disponibilidadHoraria=" + disponibilidadHoraria +
                ", experienciaLaboral=" + experienciaLaboral +
                ", apoyos=" + apoyos +
                ", encuesta=" + this.getEncuestaCandidato() +
                '}';
    }


    public CandidatoIdioma getUnCandidatoIdioma(long l) {
        for (CandidatoIdioma candidatoIdioma : this.getCandidatoIdiomas()){
            if (candidatoIdioma.getId().equals(l)){
                return candidatoIdioma;
            }
        }
        return null;
    }
}
