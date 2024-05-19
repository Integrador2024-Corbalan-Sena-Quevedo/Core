package com.mides.core.controller;

import com.mides.core.service.IFileAttachmentService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FileAttachmentController {

    @Autowired
    IFileAttachmentService fileAttachmentService;

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {

        Map<String,String> csvData = new HashMap<>();

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor, sube un archivo CSV.");
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            for (CSVRecord csvRecord : csvParser) {
                for (String header : csvParser.getHeaderNames()){
                    csvData.put(header, csvRecord.get(header));
                }
            }

            fileAttachmentService.processCSVData(csvData);
//            System.out.println("Datos del CSV");
//            csvData.forEach((header, value) -> System.out.println(header + "\n" + value));



            return ResponseEntity.ok("Archivo CSV procesado con éxito.");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo CSV.");
        }
    }

}
