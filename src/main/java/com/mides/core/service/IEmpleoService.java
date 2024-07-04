package com.mides.core.service;

import com.mides.core.model.ConocimientosEspecificosEmpleo;
import com.mides.core.model.Empleo;
import com.mides.core.model.Empresa;

import java.util.List;
import java.util.Map;

public interface IEmpleoService {

    void saveEmpleo(Empleo empleo);
    void saveConocimientoEspecificosEmplep(ConocimientosEspecificosEmpleo conocimientosEspecificosEmpleo);
    Empleo processEmpleo(List<Map<String, String>> csvData, Empresa empresa);
    void processConocimientoEspecificosEmpleo(List<Map<String, String>> csvData, Empleo empleo);

}
