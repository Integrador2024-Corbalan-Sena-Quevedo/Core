package com.mides.core.service;

import com.mides.core.model.Empleo;
import com.mides.core.model.Tarea;

import java.util.List;
import java.util.Map;

public interface ITareaService {

    void saveTarea(Tarea tarea);
    void saveTareas(List<Tarea> tareas);
    void processTarea(List<Map<String, String>> csvData, Empleo empleo);
   // List<DetalleTarea> processTareaDetalle(List<Map<String, String>> csvData);

}
