package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Discapacidad;
import com.mides.core.model.TipoDiscapacidad;

import java.util.List;
import java.util.Map;

public interface IDiscapacidadService {

    void saveDiscapacidad(Discapacidad discapacidad);
    void processDiscapacidad(List<Map<String,String>> csvData,List<TipoDiscapacidad> tipoDiscapacidades ,Candidato candidato);
}
