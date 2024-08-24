package com.mides.core.service;



import com.mides.core.model.GustoLaboral;
import com.mides.core.model.Turno;

import java.util.List;
import java.util.Map;

public interface IGustoLaboralService {

    void saveGustoLaboral(GustoLaboral gustoLaboral);
    void saveGustoLaboral(List<GustoLaboral> gustosLaborales);

    void processGustoLaboralCarga(List<Map<String, String>> csvData);

    List<GustoLaboral> getGustos();

    GustoLaboral getUnGusto(String nombre);
}
