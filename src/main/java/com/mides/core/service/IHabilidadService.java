package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Habilidad;

import java.util.List;
import java.util.Map;

public interface IHabilidadService {
    void saveHabilidad(Habilidad habilidad);
    void editHabilidad(Habilidad habilidad);
    void findHabilidad(Long id);
    void deleteHabilidad(Long id);
    void processHabilidad(List<Map<String, String>> csvData, Candidato candidato);

}
