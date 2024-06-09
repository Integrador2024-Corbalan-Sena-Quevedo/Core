package com.mides.core.service;


import com.mides.core.model.Candidato;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileAttachmentService implements IFileAttachmentService{

    @Autowired
    ICandidatoSevice candidatoSevice;
    @Autowired
    IDirreccionService dirreccionService;

    @Autowired
    ITelefonoService  telefonoService;

    @Autowired
    IEducacionService educacionService;

    @Autowired
    IInstitucionService institucionService;
    List<Map<String,String>> csvData = new ArrayList<>();

    @Override
    public void forCSVData(BufferedReader bufferedReader, CSVParser csvParser) {

        for (CSVRecord csvRecord : csvParser) { // csvRecord son los values del archivo
            Map<String,String> csvRow = new HashMap<>();
            for (String header : csvParser.getHeaderNames()){
                csvRow.put(header, csvRecord.get(header));
            }
            csvData.add(csvRow);
        }

        this.processCSVData(csvData);
    }

    @Override
    public void processCSVData(List<Map<String, String>> csvData) {
           Candidato candidato = candidatoSevice.processCandidato(csvData);
            dirreccionService.processDirreccion(csvData,candidato);
            telefonoService.processTelefono(csvData,candidato);
            institucionService.precargarInsituciones();
            educacionService.processEducacion(csvData, candidato, institucionService.getInstituciones());


    }

}
