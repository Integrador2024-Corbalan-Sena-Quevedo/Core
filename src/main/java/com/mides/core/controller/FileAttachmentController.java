package com.mides.core.controller;

import com.mides.core.service.IFileAttachmentService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

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
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withQuote('"').withIgnoreSurroundingSpaces(true));
            List<Integer> linesWithErrors = fileAttachmentService.forCSVData(reader, csvParser, type);

            if (!linesWithErrors.isEmpty()) {
                String errorMessage = String.format("Archivo subido con éxito! menos las líneas %s", linesWithErrors);
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
