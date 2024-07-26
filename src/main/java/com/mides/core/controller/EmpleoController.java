package com.mides.core.controller;

import com.mides.core.dto.EmpleoDTO;
import com.mides.core.model.Candidato;
import com.mides.core.model.Empleo;
import com.mides.core.request.EmpleoRequest;
import com.mides.core.service.ICandidatoSevice;
import com.mides.core.service.IEmpleoService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST)
public class EmpleoController{

    @Autowired
    IEmpleoService empleoService;
    @Autowired
    ICandidatoSevice candidatoSevice;

    @Autowired
    ChatGPTController chatGPTController;

    private List<Candidato> candidatos = new ArrayList<>();

    @PostMapping("/preFilto")
    public ResponseEntity<?> getCandidatosParaEmpelo(@RequestBody Long empleoId) throws IOException {
        Empleo empleo = new Empleo();
        empleo = empleoService.findById(empleoId);
        candidatos = candidatoSevice.getCandidatos();
        List<Candidato> candidatosFiltrados = empleoService.getCandidatosParaEmpleo(empleo, candidatos);

        if (!candidatosFiltrados.isEmpty()){
            return new ResponseEntity<>(candidatosFiltrados, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No hay candidatos para este empleo", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/empleos")
    public ResponseEntity<?> getEmpleos(){

        List<EmpleoDTO> empleosDTO = empleoService.getEmpleosDTO();

        if (empleosDTO.isEmpty()){
            return new ResponseEntity<>("No hay empleos disponibles", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(empleosDTO, HttpStatus.OK);
        }
    }
}
