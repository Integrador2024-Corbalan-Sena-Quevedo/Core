package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriaCandidato {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    @JsonBackReference
    private Candidato candidato;
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonBackReference
    private Usuario usuario;
    private String tipo;
    private String campo;
    private String datoAnt;
    private String datoAct;
    private String tablaAEditar;
    private Long idTablaAEditar;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE) // Especifica que la columna es de tipo DATE
    private LocalDate fechaCambio;

    public AuditoriaCandidato(Usuario usuario, String tipo, Candidato candidato, String campo, String datoAnt, String datoAct, String tablaAEditar, LocalDate fechaCambio) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.candidato = candidato;
        this.campo = campo;
        this.datoAnt = datoAnt;
        this.datoAct = datoAct;
        this.tablaAEditar = tablaAEditar;
        this.fechaCambio = fechaCambio;
    }

    public AuditoriaCandidato(Usuario usuario, String tipo, Candidato candidato, String campo, String datoAnt, String datoAct, String tablaAEditar, Long idTablaAEditar, LocalDate fechaCambio) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.candidato = candidato;
        this.campo = campo;
        this.datoAnt = datoAnt;
        this.datoAct = datoAct;
        this.tablaAEditar = tablaAEditar;
        this.idTablaAEditar = idTablaAEditar;
        this.fechaCambio = fechaCambio;
    }

    // Getters y Setters
}
