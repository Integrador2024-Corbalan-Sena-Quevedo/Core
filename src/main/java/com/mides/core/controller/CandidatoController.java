package com.mides.core.controller;

import com.mides.core.dto.CandidatoDTO;
import com.mides.core.model.Candidato;
import com.mides.core.service.ICandidatoSevice;
import com.mides.core.service.IPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CandidatoController {

    @Autowired
    IPdfService pdfService;
    @Autowired
    ICandidatoSevice candidatoSevice;

    @PostMapping("/getCv")
    public ResponseEntity<?> getCv(@RequestBody CandidatoDTO candidatoDTO) throws Exception {
        Resource resource;

        try {
            Candidato candidato = candidatoSevice.findCandidato(candidatoDTO.getDocumento());
            if (candidato.getCsvBase64() != null) {
                resource = pdfService.base64AsPdf(candidato.getNombre(), candidato.getCsvBase64());
            } else {
                return new ResponseEntity<>("El candidato no tiene cv", HttpStatus.NOT_FOUND);
            }

            if (resource != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return new ResponseEntity<>("Ha ocurrido un error a la hora de obtener el CV", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PatchMapping("/deleteCv")
    public ResponseEntity<?> deleteCv(@RequestBody Long candidatoId) throws Exception {
        try {
            candidatoSevice.deleteCv(candidatoId);
            return ResponseEntity.ok("Cv eliminado con exito");
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/uploadCV")
    public ResponseEntity<?> uploadCv(@RequestParam("file") MultipartFile file, @RequestParam Long candidatoId) throws IOException {
        try {
            Candidato candidato = candidatoSevice.findCandidatoById(candidatoId);
            if (candidato != null){
                byte[] pdfData = pdfService.pdfAsBase64(file);
                candidato.setCsvBase64(new String(pdfData, StandardCharsets.UTF_8));
                candidatoSevice.saveCandidato(candidato);
                return new ResponseEntity<>("CV cargado con exito", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No se puedo encontrar al candidato", HttpStatus.OK);
            }
        }catch (IOException e){
            throw new IOException(e);
        }

    }

}
