package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Habilidad;
import com.mides.core.repository.IHabilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class HabilidadService implements IHabilidadService {

    @Autowired
    IHabilidadRepository habilidadRepository;
    @Override
    public void saveHabilidad(Habilidad habilidad) {
        habilidadRepository.save(habilidad);
    }

    @Override
    public void editHabilidad(Habilidad habilidad) {

    }

    @Override
    public void findHabilidad(Long id) {

    }

    @Override
    public void deleteHabilidad(Long id) {

    }

    @Override
//    @Transactional
    public void processHabilidad(List<Map<String, String>> csvData, Candidato candidato) {
        Habilidad habilidad = new Habilidad();
        for (Map<String, String> row : csvData) {
            habilidad.setPower_point(Integer.parseInt(row.get("PowerPoint")));
            habilidad.setWord(Integer.parseInt(row.get("Word")));
            habilidad.setExcel(Integer.parseInt(row.get("Excel")));
            habilidad.setInternet(Integer.parseInt(row.get("Internet")));
            habilidad.setLSU(Integer.parseInt(row.get("LSU")));
            habilidad.setManejo_de_dinero(Integer.parseInt(row.get("Otras_habilidades - Maneja el dinero")));
            habilidad.setAutonomia_en_transporte_publico(Integer.parseInt(row.get("Otras_habilidades - Autonomía en el transporte público")));
            habilidad.setImagen_personal(Integer.parseInt(row.get("Otras_habilidades - Imagen personal")));
            habilidad.setOtrasHabilidades(Integer.parseInt(row.get("Otras_habilidades - Ninguna de las anteriores")));
            habilidad.setDescripcion(row.get("Desc_otras_hab"));
            habilidad.setCandidato(candidato);
        }
        this.saveHabilidad(habilidad);

    }
}
