package com.mides.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoDTO {

    private Long candidatoId;
    private String nombre;
    private String apellido;
    private String documento;

}
