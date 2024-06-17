package com.mides.core.service;


import com.mides.core.model.MotivoDesempleo;
import com.mides.core.repository.IMotivoDesempleoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class MotivoDesempleoService implements IMotivoDesempleoService{

    @Autowired
    IMotivoDesempleoRepository motivoDesempleoRepository;
    @Override
    public void saveMotivoDesempleo(MotivoDesempleo motivoDesempleo) {
        List<MotivoDesempleo> motivoDesempleosEnBase = motivoDesempleoRepository.findAll();
        for (MotivoDesempleo motivoDesempleoAux : motivoDesempleosEnBase){
            if(!motivoDesempleoAux.getMotivo().equals(motivoDesempleo.getMotivo())){
                motivoDesempleoRepository.save(motivoDesempleo);
            }
        }
        if (motivoDesempleosEnBase.isEmpty()){
            motivoDesempleoRepository.save(motivoDesempleo);
        }

    }

    @Override
    public void saveMotivosDesempleo(List<MotivoDesempleo> motivosDesempleo) {
        for (MotivoDesempleo motivoDesempleo : motivosDesempleo){
            this.saveMotivoDesempleo(motivoDesempleo);
        }

    }

    @Override
    public void processMotivoDesempleoCarga(List<Map<String, String>> csvData) {

        List<MotivoDesempleo> motivosDesempleo = new ArrayList<>();
        for(Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                MotivoDesempleo motivoDesempleoAux = null;
                String key = entry.getKey();
                if (key.startsWith("Motivo_desempleo - ")) {
                    String nombreMotivo = key.substring("Motivo_desempleo - ".length());
                    switch (nombreMotivo) {
                        case "Debió dedicarse a los quehaceres del hogar o cuidados de otras personas":
                            nombreMotivo = "HACERES_HOGAR_CUIDADOS_PERSONAS";
                            break;
                        case "Tenía problemas vinculares con los compañeros de trabajo":
                            nombreMotivo = "PROBLEMAS_CON_COMPANIERO";
                            break;
                        case "Le resultaba poca remuneración":
                            nombreMotivo = "REMUNERACION_BAJA";
                            break;
                        case "Fue despedida/o o no se renovó el contrato":
                            nombreMotivo = "DESPIDO_NO_RENOVACION";
                            break;
                        case "Motivos de salúd":
                            nombreMotivo = "SALUD";
                            break;
                        case "Motivos de accesibilidad":
                            nombreMotivo = "ACCESIBILIDAD";
                            break;
                        case "Motivos de discriminación":
                            nombreMotivo = "DISCRIMINACION";
                            break;
                        case "No tenía interés / Le interesaba trabajar en otras cosas":
                            nombreMotivo = "FALTA_INTERES";
                            break;
                        case "Otro":
                            nombreMotivo = row.get("Motivo_desempleo - Otros").replaceAll(" ", "_").toUpperCase();
                            break;
                        default:
                            break;
                    }
                            if(!nombreMotivo.equals("Otros")){
                                motivoDesempleoAux = new MotivoDesempleo(nombreMotivo);
                                motivosDesempleo.add(motivoDesempleoAux);
                            }
                    }

                }
            }

        this.saveMotivosDesempleo(motivosDesempleo);

    }

    @Override
    public List<MotivoDesempleo> getMotivos() {
      return  motivoDesempleoRepository.findAll();
    }
}
