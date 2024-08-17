package com.mides.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ActualizarSubListaCandidatoDTO {
    private String candidatoId;
    private String lista;
    private String subLista;
    private String id;
}
