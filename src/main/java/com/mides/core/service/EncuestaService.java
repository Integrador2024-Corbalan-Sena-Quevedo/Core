package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Encuesta;
import com.mides.core.repository.IEncuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void processEncuesta(List<Map<String, String>> csvData, Candidato candidato) throws ParseException {
        Encuesta encuesta = new Encuesta();
      //  SimpleDateFormat fechaNacimientoFormato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateTimeFormatter fechaCreacion = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");

        for (Map<String, String> row : csvData){
            encuesta.setEstado(row.get("Estado"));
            encuesta.setIdFlow(row.get("IdFlow"));
            encuesta.setFechaCreacion(LocalDateTime.parse(row.get("FechaCreacion"), fechaCreacion));
            encuesta.setIdFlowAFAM(row.get("IdFlowAFAM"));
            encuesta.setCreadaPor(row.get("CreadaPor"));
            encuesta.setCandidato(candidato);
        }
        this.saveEncuesta(encuesta);
    }
}
