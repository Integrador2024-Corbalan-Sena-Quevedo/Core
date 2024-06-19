package com.mides.core.controller;

import com.mides.core.service.IFileAttachmentService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST)
public class FileAttachmentController {

    @Autowired
    IFileAttachmentService fileAttachmentService;

    @PostMapping("/upload-csv")
    @ResponseBody
    public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file) {


        if (file.isEmpty()) {
            return new ResponseEntity<>("Seleccione un archivo por favor",HttpStatus.BAD_REQUEST);
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withQuote('"').withIgnoreSurroundingSpaces(true));

            fileAttachmentService.forCSVData(reader, csvParser);

            return new ResponseEntity<>("Archivo subido con éxito!",HttpStatus.OK);

        } catch (IOException  | ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error en la carga de archivo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
