package com.mides.core.service;

import com.mides.core.model.*;
import com.mides.core.repository.IApoyoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApoyoService implements IApoyoService{

    @Autowired
    IApoyoRepository apoyoRepository;
    @Override
    public void saveApoyo(Apoyo apoyo) {
        apoyoRepository.save(apoyo);
    }

    @Override
    public void saveApoyos(List<Apoyo> apoyos) {
        for (Apoyo apoyo : apoyos){
            this.saveApoyo(apoyo);
        }
    }

    @Override
    public void processApoyoCarga(List<Map<String, String>> csvData) {

        List<Apoyo> apoyos = new ArrayList<>();
        for (Map<String, String> row: csvData){
            for (Map.Entry<String, String> entry : row.entrySet()) {
                Apoyo apoyoAux = null;
                String key = entry.getKey();
                if (key.startsWith("Apoyo_cognitivo - ")) {
                    String nombreApoyoCognitivo = key.substring("Apoyo_cognitivo - ".length());
                    apoyoAux = new Cognitivo(nombreApoyoCognitivo);
                    apoyos.add(apoyoAux);
                }
                else if (key.startsWith("Apoyo_sensorial - ")){
                    String nombreApoyoSensorial= key.substring("Apoyo_sensorial - ".length());
                    apoyoAux = new Sensorial(nombreApoyoSensorial);
                    apoyos.add(apoyoAux);
                }
                else if (key.startsWith("apoyos_fisicos - ")){
                    String nombreApoyoFisico= key.substring("apoyos_fisicos - ".length());
                    apoyoAux = new Fisico(nombreApoyoFisico);
                    apoyos.add(apoyoAux);
                }
            }
        }
        this.saveApoyos(apoyos);
    }

    @Override
    public List<Apoyo> getApoyos() {
        return apoyoRepository.findAll();
    }
}