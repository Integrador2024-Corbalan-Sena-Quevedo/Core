package com.mides.core.model;



import com.mides.core.service.ExperienciaLaboral;
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
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private Habilidad habilidad;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private Salud salud;
    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidatoIdioma> candidatoIdiomas ;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private DatosAdicionalesCandidato datosAdicionalesCandidato;
    @ManyToMany
    @JoinTable(
            name = "candidato_ayudaTecnica",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "ayuda_id")
    )
    private List<AyudaTecnica> ayudaTecnicas;
    @ManyToMany
    @JoinTable(
            name = "candidato_prestacion",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "prestacion_id")
    )
    private List<Prestacion> prestaciones;
    @ManyToMany
    @JoinTable(
            name = "candidato_area",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private List<Area> areas;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private Discapacidad discapacidad;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private DisponibilidadHoraria disponibilidadHoraria;
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private ExperienciaLaboral experienciaLaboral;
    @ManyToMany
    @JoinTable(
            name = "candidato_apoyo",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "apoyo_id")
    )
    private List<Apoyo> apoyos;


}
