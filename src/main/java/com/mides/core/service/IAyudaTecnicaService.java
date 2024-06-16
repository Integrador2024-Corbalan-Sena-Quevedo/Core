package com.mides.core.service;

import com.mides.core.model.AyudaTecnica;
import com.mides.core.model.Candidato;

import java.util.List;
import java.util.Map;

public interface IAyudaTecnicaService {

    void saveAyudaTecnica(AyudaTecnica ayudaTecnica);
    void saveAyudaTecnica(List<AyudaTecnica> ayudaTecnicas);

    void processAyudaTecnicaCarga(List<Map <String,String>> csvData);
    List<AyudaTecnica> getAyudaTecnicas();
}
