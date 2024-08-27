package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Direccion;
import com.mides.core.model.Empresa;

import java.util.List;
import java.util.Map;

public interface IDireccionService {
    void saveDireccion(Direccion direccion);
    void processDireccion(List<Map<String, String>>  csvData, Candidato candidato);
    void processDireccion(List<Map<String, String>>  csvData, Empresa empresa);
}
