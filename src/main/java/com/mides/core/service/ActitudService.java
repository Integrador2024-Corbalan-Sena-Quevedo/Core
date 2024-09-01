package com.mides.core.service;

import com.mides.core.model.Actitud;
import com.mides.core.repository.IActitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ActitudService implements IActitudService{

    @Autowired
    IActitudRepository actitudRepository;
    @Override
    public void saveActitud(Actitud actitud) {
        List<Actitud> actitudesEnBase = actitudRepository.findAll();
        for (Actitud actitudAux : actitudesEnBase){
            if (!actitudAux.getNombre().equals(actitud.getNombre())){
                actitudRepository.save(actitud);
            }
        }
        if (actitudesEnBase.isEmpty()){
            actitudRepository.save(actitud);
        }
    }

    @Override
    public void saveActitudes(List<Actitud> actitudes) {
        for (Actitud actitud : actitudes){
            this.saveActitud(actitud);
        }

    }

    @Override
    public void processActitudCarga(List<Map<String, String>> csvData) {

        List<Actitud> actitudes = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                Actitud actitudAux = null;
                String key = entry.getKey();
                if (key.startsWith("Actitud_empleo - ")) {
                    String nombreActitud = key.substring("Actitud_empleo - ".length());
                    actitudAux = new Actitud(nombreActitud);
                        actitudes.add(actitudAux);
                }
            }
        }
    this.saveActitudes(actitudes);
    }

    @Override
    public List<Actitud> getActitudes() {
        return actitudRepository.findAll();
    }

    @Override
    public Actitud getUnActitud(String nombre) {
        for (Actitud actitud : getActitudes()) {
            if (actitud.getNombre().equals(nombre)) {
                return actitud;
            }
        }
        return null;
    }
}
