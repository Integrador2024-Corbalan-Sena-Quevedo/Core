package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Empresa;
import com.mides.core.model.EncuestaCandidato;
import com.mides.core.model.EncuestaEmpresa;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IEncuestaService {
    void saveEncuesta(EncuestaCandidato encuestaCandidato);
    void saveEncuestaEmpresa(EncuestaEmpresa encuestaEmpresa);
    void processEncuestaCandidato(List<Map<String,String>> csvData, Candidato candidato) throws ParseException;
    void processEncuestaEmpresa(List<Map<String,String>> csvData, Empresa empresa) throws ParseException;
    List<EncuestaCandidato> getEncuestasCandidato();

}
