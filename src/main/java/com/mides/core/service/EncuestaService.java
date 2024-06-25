package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Encuesta;
import com.mides.core.repository.IEncuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class EncuestaService implements  IEncuestaService{

    @Autowired
    IEncuestaRepository encuestaRepository;
    @Override
    public void saveEncuesta(Encuesta encuesta) {
        encuestaRepository.save(encuesta);
    }

    @Override
//    @Transactional
    public void processEncuesta(List<Map<String, String>> csvData, Candidato candidato) throws ParseException {
        Encuesta encuesta = new Encuesta();
        DateTimeFormatter fechaCreacionFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        for (Map<String, String> row : csvData){
            String fechaCreacionString = row.get("FechaCreacion");
            String fechaFinalizacionString = row.get("FechaFinalizacion");
            String fechaCreacionSinAMPM = fechaCreacionString.replaceAll("\\s+[AP]M$", "");
            String fechaFinalizacionSinAMPM = fechaFinalizacionString.replaceAll("\\s+[AP]M$", "");
            encuesta.setEstado(row.get("Estado"));
            encuesta.setIdFlow(row.get("IdFlow"));
            encuesta.setIdFlowAFAM(row.get("IdFlowAFAM"));
            encuesta.setCreadaPor(row.get("CreadaPor"));
            encuesta.setCandidato(candidato);
            try {
                LocalDateTime fechaCreacion = LocalDateTime.parse(fechaCreacionSinAMPM, fechaCreacionFormatter);
                LocalDateTime fechaFinalizacion = LocalDateTime.parse(fechaFinalizacionSinAMPM, fechaCreacionFormatter);
                encuesta.setFechaCreacion(fechaCreacion);
                encuesta.setFechaFinalizacion(fechaFinalizacion);
            } catch (Exception e) {
                System.err.println("Error al parsear la fecha: " + e.getMessage());
            }
        }
        this.saveEncuesta(encuesta);
    }
}
