package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Empresa;
import com.mides.core.model.EncuestaCandidato;
import com.mides.core.model.EncuestaEmpresa;
import com.mides.core.repository.IEncuestaEmpresaRepository;
import com.mides.core.repository.IEncuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class EncuestaService implements  IEncuestaService{

    @Autowired
    IEncuestaRepository encuestaRepository;

    @Autowired
    IEncuestaEmpresaRepository encuestaEmpresaRepository;
    @Override
    public void saveEncuesta(EncuestaCandidato encuestaCandidato) {
        encuestaRepository.save(encuestaCandidato);
    }

    @Override
    public void saveEncuestaEmpresa(EncuestaEmpresa encuestaEmpresa) {
        encuestaEmpresaRepository.save(encuestaEmpresa);
    }

    @Override
    public void processEncuestaCandidato(List<Map<String, String>> csvData, Candidato candidato) {
        EncuestaCandidato encuestaCandidato = new EncuestaCandidato();
        DateTimeFormatter fechaCreacionFormatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm:ss");

        for (Map<String, String> row : csvData){
            String fechaCreacionString = row.get("FechaCreacion");
            String fechaFinalizacionString = row.get("FechaFinalizacion");
            String fechaCreacionSinAMPM = fechaCreacionString.replaceAll("\\s+[AP]M$", "");
            String fechaFinalizacionSinAMPM = fechaFinalizacionString.replaceAll("\\s+[AP]M$", "");
            encuestaCandidato.setEstado(row.get("Estado"));
            encuestaCandidato.setIdFlow(row.get("IdFlow"));
            encuestaCandidato.setIdFlowAFAM(row.get("IdFlowAFAM"));
            encuestaCandidato.setCreadaPor(row.get("CreadaPor"));
            encuestaCandidato.setCandidato(candidato);
            try {
                LocalDateTime fechaCreacion = LocalDateTime.parse(fechaCreacionSinAMPM, fechaCreacionFormatter);
                LocalDateTime fechaFinalizacion = LocalDateTime.parse(fechaFinalizacionSinAMPM, fechaCreacionFormatter);
                encuestaCandidato.setFechaCreacion(fechaCreacion);
                encuestaCandidato.setFechaFinalizacion(fechaFinalizacion);
            } catch (Exception e) {
                System.err.println("Error al parsear la fecha: " + e.getMessage());
            }
        }
        this.saveEncuesta(encuestaCandidato);
    }

    @Override
    public void processEncuestaEmpresa(List<Map<String, String>> csvData, Empresa empresa) {
        EncuestaEmpresa encuestaEmpresa = new EncuestaEmpresa();
        DateTimeFormatter fechaCreacionFormato = DateTimeFormatter.ofPattern("[d/M/yyyy][dd/MM/yyyy]");

        for (Map<String, String> row : csvData){
            encuestaEmpresa.setIdEncuesta(Long.parseLong(row.get("Id Encuesta")));
            encuestaEmpresa.setObservaciones(row.get("Obs"));
            encuestaEmpresa.setComentarios(row.get("Comentarios:"));
            encuestaEmpresa.setCalificacionEncuesta(row.get("¿Cómo calificarías esta gestión?:"));
            try {
                LocalDate fechaCreacion = LocalDate.parse(row.get("Fecha de Creación"), fechaCreacionFormato);
                encuestaEmpresa.setFechaDeCreacion(fechaCreacion);
            }catch (Exception e){
                System.err.println("Error al parsear la fecha: " + e.getMessage());
            }
        }
        encuestaEmpresa.setEmpresa(empresa);
        this.saveEncuestaEmpresa(encuestaEmpresa);
    }
}
