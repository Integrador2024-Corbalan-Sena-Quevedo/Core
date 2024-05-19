package com.mides.core.service;


import com.mides.core.model.Candidato;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class FileAttachmentService implements IFileAttachmentService{

    @Override
    public void processCSVData(Map<String, String> csvData) {
        Candidato candidato = new Candidato();
        candidato.setCI(csvData.get("CI"));
        candidato.setNombre(csvData.get("nombre"));
        candidato.setApellido(csvData.get("apellido"));
        candidato.setEdad(Integer.parseInt(csvData.get("edad")));
        candidato.setDepartamento(csvData.get("departamento"));

        System.out.println(candidato.toString());
    }
}
