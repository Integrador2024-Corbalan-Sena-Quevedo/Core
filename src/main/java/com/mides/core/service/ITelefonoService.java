package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Telefono;

import java.util.List;
import java.util.Map;

public interface ITelefonoService {
    List<Telefono> getTelefonos();
    void saveTelefono(Telefono telefono);
    Telefono findTelefono(Long id);
    void editTelefono(Telefono telefono);

    void processTelefono(List<Map<String, String>> csvData, Candidato candidato);
}
