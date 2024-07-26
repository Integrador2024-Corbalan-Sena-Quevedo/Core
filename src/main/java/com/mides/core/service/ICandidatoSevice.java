package com.mides.core.service;

import com.mides.core.model.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ICandidatoSevice {

    List<Candidato> getCandidatos();
    void saveCandidato(Candidato candidato);
    void deleteCandidato(Long id);
    Candidato findCandidato(String documento);
    Candidato findCandidatoById(Long id);
    void editCandidato(Candidato candidato);
    Candidato processCandidato(List<Map<String, String>>  csvData, List<AyudaTecnica> ayudaTecnicas, List<Prestacion> prestaciones, List<Area> areas, List<Apoyo> apoyos) throws ParseException;
}
