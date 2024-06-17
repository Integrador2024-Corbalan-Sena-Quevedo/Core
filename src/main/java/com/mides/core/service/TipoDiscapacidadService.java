package com.mides.core.service;

import com.mides.core.model.TipoDiscapacidad;
import com.mides.core.repository.ITipoDiscapacidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TipoDiscapacidadService implements  ITipoDiscapacidadService{

    @Autowired
    ITipoDiscapacidadRepository tipoDiscapacidadRepository;
    @Override
    public void saveTipoDiscapacidades(List<TipoDiscapacidad> tipoDiscapacidades) {
        for (TipoDiscapacidad tipoDiscapacidadAux : tipoDiscapacidades){
            this.saveTipoDiscapacidad(tipoDiscapacidadAux);
        }

    }

    @Override
    public void saveTipoDiscapacidad(TipoDiscapacidad tipoDiscapacidad) {
        List<TipoDiscapacidad> tiposDiscapacidades = tipoDiscapacidadRepository.findAll();
        for (TipoDiscapacidad tipoAux : tiposDiscapacidades){
            if(!tipoAux.getNombre().equals(tipoDiscapacidad.getNombre())){
                tipoDiscapacidadRepository.save(tipoDiscapacidad);
            }
        }
        if (tiposDiscapacidades.isEmpty()){
            tipoDiscapacidadRepository.save(tipoDiscapacidad);
        }

    }

    @Override
    public void processTipoDiscapacidadCarga(List<Map<String, String>> csvData) {
        List<TipoDiscapacidad> tipoDiscapacidades = new ArrayList<>();

        for(Map<String, String> row : csvData) {
            TipoDiscapacidad tipoDiscapacidadAux = null;
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("Tipo_discapacidad - ")) {
                    String[] parts = key.substring("Tipo_discapacidad - ".length()).split(" - ", 2);
                    if (parts.length == 2) {
                        String nombreTipo = parts[0].trim();
                        String discipcionTipo = parts[1].trim();
                        tipoDiscapacidadAux = new TipoDiscapacidad(nombreTipo, discipcionTipo);
                        tipoDiscapacidades.add(tipoDiscapacidadAux);
                    }
                }
            }
        }
        this.saveTipoDiscapacidades(tipoDiscapacidades);
    }

    @Override
    public List<TipoDiscapacidad> getTipoDiscapacidades() {
        return tipoDiscapacidadRepository.findAll();
    }
}
