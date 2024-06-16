package com.mides.core.service;

import com.mides.core.model.GustoLaboral;
import com.mides.core.repository.IGustoLaboralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GustoLaboralService implements IGustoLaboralService{
    @Autowired
    IGustoLaboralRepository gustoLaboralRepository;
    @Override
    public void saveGustoLaboral(GustoLaboral gustoLaboral) {
        List<GustoLaboral> gustoLaboralesEnBase = gustoLaboralRepository.findAll();
        for (GustoLaboral gustoLaboralAux : gustoLaboralesEnBase){
            if (!gustoLaboralAux.getGusto().equals(gustoLaboral.getGusto())){
                gustoLaboralRepository.save(gustoLaboral);
            }
        }
        if (gustoLaboralesEnBase.isEmpty()){
            gustoLaboralRepository.save(gustoLaboral);
        }

    }

    @Override
    public void saveGustoLaboral(List<GustoLaboral> gustosLaborales) {
        for (GustoLaboral gustoLaboral : gustosLaborales){
            this.saveGustoLaboral(gustoLaboral);
        }

    }

    @Override
    public void processGustoLaboralCarga(List<Map<String, String>> csvData) {
//        List<GustoLaboral> gustoLaborales = new ArrayList<>();
//        for(Map<String, String> row : csvData) {
//            for (Map.Entry<String, String> entry : row.entrySet()) {
//                GustoLaboral gustoLaboralAux = null;
//                String key = entry.getKey();
//                if (key.startsWith("Gustos - ")) {
//                    String nombreGusto = key.substring("Gustos - ".length());
//                    if (nombreGusto.equals("Las tareas que desempeñaba")) {
//                        nombreGusto = GustoLaboral.gusto.TAREAS_QUE_DESEMPENIABA.name();
//                    }
//                    if (nombreGusto.equals("La remuneración")) {
//                        nombreGusto = GustoLaboral.gusto.REMUNERACION.name();
//                    }
//                    if (nombreGusto.equals("El relacionamiento laboral")) {
//                        nombreGusto = GustoLaboral.gusto.RELACIONAMIENTO_LABORAL.name();
//                    }
//                    if (nombreGusto.equals("Carga horaria")) {
//                        nombreGusto = GustoLaboral.gusto.CARGA_HORARIA.name();
//                    }
//                    if (nombreGusto.equals("Accesibilidad del puesto de trabajo")) {
//                        nombreGusto = GustoLaboral.gusto.ACCESIBILIDAD.name();
//                    }
//                    if (nombreGusto.equals("Otros")) {
//                        if (row.get("Gustos - Otros") != null && !row.get("Gustos - Otros").equals("0") && !row.get("Gustos - Otros").equals("1")) {
//                            nombreGusto = row.get("Gustos - Otros");
//                            GustoLaboral gustoLaboralOtro = new GustoLaboral(nombreGusto);
//                            gustoLaboralOtro.setGusto(GustoLaboral.gusto.OTRO);
//                            gustoLaborales.add(gustoLaboralOtro);
//                        }
//                    }
//                    if(!row.get("Gustos - Otros").equals(nombreGusto)){
//                        gustoLaboralAux = new GustoLaboral(GustoLaboral.gusto.valueOf(nombreGusto));
//                        gustoLaborales.add(gustoLaboralAux);
//                    }
//                }
//            }
//        }
//        this.saveGustoLaboral(gustoLaborales);
        List<GustoLaboral> gustoLaborales = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                GustoLaboral gustoLaboralAux = null;
                String key = entry.getKey();
                if (key.startsWith("Gustos - ")) {
                    String nombreGusto = key.substring("Gustos - ".length()).replaceAll(" ", "_");

                    switch (nombreGusto) {
                        case "Las_tareas_que_desempeñaba":
                            nombreGusto = "TAREAS_QUE_DESEMPENIABA";
                            break;
                        case "La_remuneración":
                            nombreGusto = "REMUNERACION";
                            break;
                        case "El_relacionamiento_laboral":
                            nombreGusto = "RELACIONAMIENTO_LABORAL";
                            break;
                        case "Carga_horaria":
                            nombreGusto = "CARGA_HORARIA";
                            break;
                        case "Accesibilidad_del_puesto_de_trabajo":
                            nombreGusto = "ACCESIBILIDAD";
                            break;
                        case "Otros":
                            if (row.get("Gustos - Otros") != null && !row.get("Gustos - Otros").equals("0") && !row.get("Gustos - Otros").equals("1")) {
                                nombreGusto = row.get("Gustos - Otros").replaceAll(" ", "_").toUpperCase();
                            }
                            break;
                        default:
                            break;
                    }

                    gustoLaboralAux = new GustoLaboral(nombreGusto);
                    gustoLaborales.add(gustoLaboralAux);
                }
            }
        }
        this.saveGustoLaboral(gustoLaborales);
    }

    @Override
    public List<GustoLaboral> getGustos() {
        return  gustoLaboralRepository.findAll();
    }
}
