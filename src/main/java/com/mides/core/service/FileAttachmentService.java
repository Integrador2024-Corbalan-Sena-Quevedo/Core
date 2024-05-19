package com.mides.core.service;


import com.mides.core.model.Candidato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FileAttachmentService implements IFileAttachmentService{
    @Autowired
    ICandidatoSevice candidatoSevice;

    @Override
    public void processCSVData(List<Map<String, String>> csvData) {
        for (Map<String, String> row : csvData) {
            Candidato candidato = new Candidato();
            candidato.setCI(row.get("CI"));
            candidato.setNombre(row.get("nombre"));
            candidato.setApellido(row.get("apellido"));
            candidato.setEdad(Integer.parseInt(row.get("edad")));
            candidato.setDepartamento(row.get("departamento"));

            candidatoSevice.saveCandidato(candidato);
        }
    }
}
