package com.mides.core.service;

import com.mides.core.dto.EmpleoDTO;
import com.mides.core.model.Candidato;
import com.mides.core.model.ConocimientosEspecificosEmpleo;
import com.mides.core.model.Empleo;
import com.mides.core.model.Empresa;
import com.mides.core.request.EmpleoRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IEmpleoService {

    void saveEmpleo(Empleo empleo);
    void saveConocimientoEspecificosEmplep(ConocimientosEspecificosEmpleo conocimientosEspecificosEmpleo);
    Empleo processEmpleo(List<Map<String, String>> csvData, Empresa empresa);
    void processConocimientoEspecificosEmpleo(List<Map<String, String>> csvData, Empleo empleo);
    List<Candidato> getCandidatosParaEmpleo(Empleo empleo, List<Candidato> candidatos);
    Empleo findById(Long id);
    List<Empleo> getEmpleos();
    List<EmpleoDTO> getEmpleosDTO();

    ResponseEntity<?> candidatosSegueridosParaEmpleo(EmpleoRequest empleoRequest) throws IOException;
}
