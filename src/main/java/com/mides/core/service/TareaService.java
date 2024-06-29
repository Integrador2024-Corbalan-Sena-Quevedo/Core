package com.mides.core.service;

import com.mides.core.model.Empleo;
import com.mides.core.model.Tarea;
import com.mides.core.repository.ITareaEsencialRepository;
import com.mides.core.repository.ITareaNoEsencialRepository;
import com.mides.core.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TareaService implements  ITareaService{

    @Autowired
    private ITareaEsencialRepository tareaEsencialRepository;
    @Autowired
    private ITareaNoEsencialRepository tareaNoEsencialRepository;


    @Override
    public void saveTarea(Tarea tarea) {
        if (tarea instanceof TareaEsencial) {
          tareaEsencialRepository.save((TareaEsencial) tarea);
        }
        if (tarea instanceof TareaNoEsencial) {
            tareaNoEsencialRepository.save((TareaNoEsencial) tarea);
        }
    }

    @Override
    public void saveTareas(List<Tarea> tareas) {

    }

    @Override
    public void processTarea(List<Map<String, String>> csvData,  Empleo empleo) {
        TareaEsencial tareaEsencial = new TareaEsencial();
        TareaNoEsencial tareaNoEsencial = new TareaNoEsencial();

        for (Map<String, String> row : csvData){
            List<DetalleTarea> detalleTareasEsenciales = processTareaDetalle(row.get("Detalle de las tareas esenciales:"), tareaEsencial, null);
            tareaEsencial.setDetalleTarea(detalleTareasEsenciales);
            tareaEsencial.setNombre(row.get("Tareas esenciales:"));

            List<DetalleTarea> detalleTareasNoEsenciales = processTareaDetalle(row.get("Detalle de las tareas no esenciales:"), null, tareaNoEsencial);
            tareaNoEsencial.setDetalleTarea(detalleTareasNoEsenciales);
            tareaNoEsencial.setNombre(row.get("Tareas no esenciales:"));
        }
        tareaEsencial.setEmpleo(empleo);
        tareaNoEsencial.setEmpleo(empleo);
        this.saveTarea(tareaEsencial);
        this.saveTarea(tareaNoEsencial);
    }

    public List<DetalleTarea> processTareaDetalle(String detalleTareaString, TareaEsencial tareaEsencial, TareaNoEsencial tareaNoEsencial) {
        List<DetalleTarea> detalleTareas = new ArrayList<>();

        String[] partes = detalleTareaString.split("\\d+\\."); // Dividir por número seguido de punto
        for (String parte : partes) {
            if (!parte.trim().isEmpty()) {
                DetalleTarea detalleTarea = new DetalleTarea();
                detalleTarea.setDetalle(parte.trim().replaceAll("_", ", ")); // Reemplazar _ por comas
                if (tareaEsencial != null) {
                    detalleTarea.setTarea(tareaEsencial);
                }
                if (tareaNoEsencial != null) {
                    detalleTarea.setTarea(tareaNoEsencial);
                }
                detalleTareas.add(detalleTarea);
            }
        }
        return detalleTareas;
    }
}
