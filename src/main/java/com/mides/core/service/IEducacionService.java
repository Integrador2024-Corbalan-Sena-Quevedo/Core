package com.mides.core.service;


import com.mides.core.model.Candidato;
import com.mides.core.model.Educacion;
import com.mides.core.model.Institucion;

import java.util.List;
import java.util.Map;

public interface IEducacionService {
    void saveEducacion(Educacion educacion);
    Educacion findEducacion(Long id);
    void editEducacion(Educacion educacion);

    void processEducacion(List<Map<String, String>> csvData, Candidato candidato, List<Institucion> institucions);
}
