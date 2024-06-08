package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Telefono;
import com.mides.core.repository.ITelefonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class TelefonoService implements ITelefonoService {

    @Autowired
    ITelefonoRepository telefonoRepository;

    @Override
    public List<Telefono> getTelefonos() {
        return null;
    }

    @Override
    public void saveTelefono(Telefono telefono) {
        telefonoRepository.save(telefono);
    }

    @Override
    public Telefono findTelefono(Long id) {
        return null;
    }

    @Override
    public void editTelefono(Telefono telefono) {

    }

    @Override
    public void processTelefono(List<Map<String, String>> csvData, Candidato candidato) {
        Telefono telefono = new Telefono();
        for (Map<String, String> row : csvData) {
            telefono.setNumeroUno(row.get("Tel_1"));
            telefono.setDuenioUno(row.get("Duenio_tel1"));
            telefono.setNumeroDos(row.get("Tel_2"));
            telefono.setDuenioDos(row.get("Duenio_tel2"));
            telefono.setCandidato(candidato);
        }
        this.telefonoRepository.save(telefono);
    }
}
