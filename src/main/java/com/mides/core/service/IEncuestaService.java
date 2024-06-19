package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Encuesta;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IEncuestaService {
    void saveEncuesta(Encuesta encuesta);
    void processEncuesta(List<Map<String,String>> csvData, Candidato candidato) throws ParseException;

}
