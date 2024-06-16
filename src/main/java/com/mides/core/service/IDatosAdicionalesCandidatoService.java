package com.mides.core.service;


import com.mides.core.model.Candidato;
import com.mides.core.model.DatosAdicionalesCandidato;

import java.util.List;
import java.util.Map;

public interface IDatosAdicionalesCandidatoService {

    void saveDatosAdicionalesCandidato(DatosAdicionalesCandidato datosAdicionalesCandidato);
    void processDatosAdicionalesCandidato(List<Map<String, String>> csvData, Candidato candidato);
}
