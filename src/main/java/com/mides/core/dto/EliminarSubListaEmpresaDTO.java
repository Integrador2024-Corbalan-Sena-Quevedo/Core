package com.mides.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EliminarSubListaEmpresaDTO {
    private String empresaId;
    private String empleoId;
    private String userName;
    private String lista;
    private String subLista;
    private String id;
    private String nombreAEliminar;
    private String posAElim;
}
