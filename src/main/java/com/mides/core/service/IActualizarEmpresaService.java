package com.mides.core.service;

public interface IActualizarEmpresaService {
    void actualizarCampo(String empresaId, String empleoId, String userName, String campo, String datoAct, String datoAnt, String lista, String subLista) throws Exception;

    void agregarASubLista(String empresaId, String empleoId, String userName, String lista, String subLista, String id) throws Exception;

    void elimnarSubLista(String empresaId, String empleoId, String userName, String lista, String subLista, String id, String nombreAEliminar, String posAElim) throws Exception;
}
