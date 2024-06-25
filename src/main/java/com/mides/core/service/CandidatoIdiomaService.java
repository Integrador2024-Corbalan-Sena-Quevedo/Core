package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.CandidatoIdioma;
import com.mides.core.model.Idioma;
import com.mides.core.repository.ICandidatoIdiomaRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CandidatoIdiomaService implements ICandidatoIdiomaService{

    @Autowired
    ICandidatoIdiomaRepository candidatoIdiomaRepository;
    @Override
    public void saveCandidatoIdioma(CandidatoIdioma candidatoIdioma) {
        candidatoIdiomaRepository.save(candidatoIdioma);
    }

    @Override
//    @Transactional
    public void processCandidatoIdioma(List<Map<String, String>> csvData, Candidato candidato, List<Idioma> idiomas) {

        for (Map<String, String> row : csvData) {
            if (row.get("Ingles").equals("1")) {
                CandidatoIdioma candidatoIdiomaIngles = new CandidatoIdioma();
                candidatoIdiomaIngles.setNivel(row.get("Nivel_ingles"));
                for (Idioma idioma : idiomas) {
                    if (idioma.getNombre().equals("Ingles")) {
                        candidatoIdiomaIngles.setIdioma(idioma);
                    }
                }
                candidatoIdiomaIngles.setCandidato(candidato);
                this.saveCandidatoIdioma(candidatoIdiomaIngles);
            }
            if (row.get("Portugues").equals("1")) {
                CandidatoIdioma candidatoIdiomaPotugues = new CandidatoIdioma();
                candidatoIdiomaPotugues.setNivel(row.get("Nivel_portugues"));
                for (Idioma idioma : idiomas) {
                    if (idioma.getNombre().equals("Portugues")) {
                        candidatoIdiomaPotugues.setIdioma(idioma);
                    }
                }
                candidatoIdiomaPotugues.setCandidato(candidato);
                this.saveCandidatoIdioma(candidatoIdiomaPotugues);
            }
            if (row.get("Otro_idioma").equals("1")) {
                CandidatoIdioma candidatoIdiomaOtos = new CandidatoIdioma();
                candidatoIdiomaOtos.setNivel(row.get("Nivel_otro"));
                for (Idioma idioma : idiomas) {
                    if (idioma.getNombre().equals(row.get("Cual_otro"))) {
                        candidatoIdiomaOtos.setIdioma(idioma);
                    }
                }
                candidatoIdiomaOtos.setCandidato(candidato);
                this.saveCandidatoIdioma(candidatoIdiomaOtos);
            }

        }

    }
}
