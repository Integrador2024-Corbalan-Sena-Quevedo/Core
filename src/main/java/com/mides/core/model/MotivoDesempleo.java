package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mides.core.service.ExperienciaLaboral;
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
public class MotivoDesempleo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToMany(mappedBy = "motivosDesempleo")
    @JsonBackReference
    private List<ExperienciaLaboral> experienciaLaborales;

    private String motivo;

    public MotivoDesempleo(String motivo) {
        this.motivo = motivo;
    }

}
