package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.CandidatoIdioma;
import com.mides.core.model.Idioma;

import java.util.List;
import java.util.Map;

public interface ICandidatoIdiomaService {

    void saveCandidatoIdioma(CandidatoIdioma candidatoIdioma);
    void processCandidatoIdioma(List<Map<String, String>> csvData, Candidato candidato, List<Idioma> idiomas);
}
