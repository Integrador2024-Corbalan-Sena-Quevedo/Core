package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Salud;

import java.util.List;
import java.util.Map;

public interface ISaludService {

    void saveSalud(Salud salud);
    void editSalud(Salud salud);
    void findSalud(Long id);
    void deleteSalud(Long id);
    void processSalud(List<Map<String, String>> csvData, Candidato candidato);
}
