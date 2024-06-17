package com.mides.core.model;

import com.mides.core.service.ExperienciaLaboral;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.StringReader;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GustoLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToMany(mappedBy = "gustosLaborales")
    private List<ExperienciaLaboral> experienciaLaborales;
    private String gusto;

    public GustoLaboral(String gusto) {
        this.gusto = gusto;
    }
}
