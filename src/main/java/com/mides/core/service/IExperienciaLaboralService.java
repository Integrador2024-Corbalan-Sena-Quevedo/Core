package com.mides.core.service;

import com.mides.core.model.*;

import java.util.List;
import java.util.Map;

public interface IExperienciaLaboralService {

    void saveExperienciaLaboral(ExperienciaLaboral experienciaLaboral);
    void processExperienciaLaboral(List<Map<String, String>> csvData, List<GustoLaboral> gustoLaborales, List<MotivoDesempleo> motivoDesempleos, List<Actitud> actitudes, Candidato candidato);
}
