package com.mides.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ActualizarCampoRequest {
    private String candidatoId;
    private String campo;
    private String datoAct;
    private String datoAnt;
    private String lista;
    private String subLista;

}
