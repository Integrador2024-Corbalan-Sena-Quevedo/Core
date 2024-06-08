package com.mides.core.service;

import com.mides.core.model.Candidato;

import java.util.List;
import java.util.Map;

public interface ICandidatoSevice {

    List<Candidato> getCandidatos();
    void saveCandidato(Candidato candidato);
    void deleteCandidato(Long id);
    Candidato findCandidato(Long id);
    void editCandidato(Candidato candidato);
    Candidato processCandidato(List<Map<String, String>>  csvData);
}
