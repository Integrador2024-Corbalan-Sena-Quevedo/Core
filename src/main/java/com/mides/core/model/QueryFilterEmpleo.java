package com.mides.core.model;

import com.mides.core.enums.NivelEducativo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryFilterEmpleo {
    private String cargaHorariaSemanal;
    private String categoria;
    private String remuneracionOfrecida;
    private Integer edadMinima;
    private Integer edadMaxima;
    private String experienciaMinima;
   // private String formacionAcademica;
    private NivelEducativo formacionAcademica;
    private String departamento;
    private String localidades;
    private String implicaDesplazamientos;
    private String ingles;
    private String portugues;
    private String computacion;
    private String libretaConducir;
}
