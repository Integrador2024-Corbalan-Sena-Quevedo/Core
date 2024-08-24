package com.mides.core.service;

public interface IActualizarCandidatoService {

    void actualizarCampo(String candidatoId, String campo, String dato, String datoAnt, String lista, String subLista) throws Exception;

    void elimnarSubLista(String candidatoId, String lista, String subLista, String idAEliminar) throws Exception;

    void agregarASubLista(String candidatoId, String lista, String subLista, String id) throws Exception;
}