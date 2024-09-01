package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Discapacidad;
import com.mides.core.model.Prestacion;
import com.mides.core.model.TipoDiscapacidad;
import com.mides.core.repository.IDiscapacidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DiscapacidadService implements  IDiscapacidadService{
    @Autowired
    IDiscapacidadRepository discapacidadRepository;
    @Override
    public void saveDiscapacidad(Discapacidad discapacidad) {
        discapacidadRepository.save(discapacidad);
    }

    @Override
//    @Transactional
    public void processDiscapacidad(List<Map<String, String>> csvData, List<TipoDiscapacidad> tipoDiscapacidades, Candidato candidato) {
        Discapacidad discapacidad = new Discapacidad();
        for (Map<String, String> row : csvData){
            discapacidad.setTipoDiscapacidades(getTipos(csvData, tipoDiscapacidades));
            discapacidad.setCandidato(candidato);
            discapacidad.setDiagnostico(row.get("Diagnóstico"));
        }
        this.saveDiscapacidad(discapacidad);
    }

    private List<TipoDiscapacidad> getTipos(List<Map<String, String>> csvData, List<TipoDiscapacidad> tipoDiscapacidades) {
        List<TipoDiscapacidad> discapacidadesDelCandidato = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("Tipo_discapacidad - ") && "1".equals(value)) {
                    String nombreTipoDiscapacidad = key.substring("Tipo_discapacidad - ".length());
                    if (nombreTipoDiscapacidad.contains(" - ")) {
                        nombreTipoDiscapacidad = nombreTipoDiscapacidad.split(" - ")[0].trim();
                    }
                    for (TipoDiscapacidad tipoDiscapacidad : tipoDiscapacidades) {
                        if (tipoDiscapacidad.getNombre().equals(nombreTipoDiscapacidad)) {
                            discapacidadesDelCandidato.add(tipoDiscapacidad);
                        }
                    }
                }
            }
        }
        return  discapacidadesDelCandidato;
    }
}
