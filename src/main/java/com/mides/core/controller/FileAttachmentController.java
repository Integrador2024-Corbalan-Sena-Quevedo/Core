package com.mides.core.controller;

import com.mides.core.service.IFileAttachmentService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST)
public class FileAttachmentController {

    @Autowired
    IFileAttachmentService fileAttachmentService;


    @PostMapping("/upload-csv")


    @ResponseBody
    public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {

        if (file.isEmpty()) {
            return new ResponseEntity<>("Seleccione un archivo por favor",HttpStatus.BAD_REQUEST);
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String headerLine = reader.readLine();

            if (headerLine.startsWith("Obs") && type.equals("CANDIDATE")) {
                return new ResponseEntity<>("El archivo proporcionado corresponde a una Empresa. Verifique el tipo seleccionado. ", HttpStatus.BAD_REQUEST);
            } else if (headerLine.startsWith("Id") && type.equals("COMPANY")) {
                return new ResponseEntity<>("El archivo proporcionado corresponde a un Candidato. Verifique el tipo seleccionado. ", HttpStatus.BAD_REQUEST);
            }

            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withQuote('"').withIgnoreSurroundingSpaces(true));

            List<Integer> linesWithErrors = fileAttachmentService.forCSVData(reader, csvParser, type);

            if (!linesWithErrors.isEmpty()) {
                String errorMessage = String.format("Archivo cargado pero las líneas %s", linesWithErrors + " no fueron procesadas");
                return new  ResponseEntity<>(errorMessage, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Archivo subido con éxito!",HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error en la carga de archivo! " ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
