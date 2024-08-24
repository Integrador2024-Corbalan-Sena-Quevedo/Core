package com.mides.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ActualizarEmpresaRequest {
    private String empresaId;
    private String empleoId;
    private String userName;
    private String campo;
    private String datoAct;
    private String datoAnt;
    private String lista;
    private String subLista;
}
