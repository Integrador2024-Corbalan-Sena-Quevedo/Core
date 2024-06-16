package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Salud;
import com.mides.core.repository.ISaludRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SaludService implements ISaludService{

    @Autowired
    ISaludRepository saludRepository;


    @Override
    public void saveSalud(Salud salud) {
        saludRepository.save(salud);
    }

    @Override
    public void editSalud(Salud salud) {

    }

    @Override
    public void findSalud(Long id) {

    }

    @Override
    public void deleteSalud(Long id) {

    }

    @Override
    public void processSalud(List<Map<String, String>> csvData, Candidato candidato) {
        Salud salud = new Salud();

        for(Map<String, String> row : csvData){
            salud.setAtencionMedica(row.get("At_médica"));
            salud.setCarnetSalud(row.get("Carnet_salúd"));
            salud.setMedicamento(row.get("Medicamentos"));
            salud.setSaludMental(row.get("salud_mental"));
            salud.setCualesMedicamentos(row.get("Cuales_medicamentos"));
            salud.setCandidato(candidato);
        }
        this.saveSalud(salud);
    }
}
