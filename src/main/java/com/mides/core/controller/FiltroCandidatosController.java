package com.mides.core.controller;

import com.mides.core.model.Candidato;
import com.mides.core.model.Cliente;
import com.mides.core.service.IFiltroCandidatosService;
import com.mides.core.specification.FiltroCandidato;
import com.mides.core.specification.SearchCandidatoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/filtro")
@CrossOrigin(origins = "http://mides-web.s3-website-us-east-1.amazonaws.com", methods = { RequestMethod.POST, RequestMethod.PUT })
public class FiltroCandidatosController {
    @Autowired
    IFiltroCandidatosService filtroCandidatosService;

    private final ObjectMapper objectMapper;

    public FiltroCandidatosController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @PostMapping("/candidatos")
        public ResponseEntity<?> filtrado(@RequestBody  Map<String, List<FiltroCandidato>> filtrosMap) {
            List<FiltroCandidato> filtros = filtrosMap.get("filtros");

            try {
                Map<Long, Cliente> candidatos;
                if( filtros == null || filtros.isEmpty() ){
                    candidatos = filtroCandidatosService.todosCandidatos();
                }else{
                    SearchCandidatoSpecification criteria = new SearchCandidatoSpecification(filtros);
                    candidatos = filtroCandidatosService.filtrarCandidatos(criteria);
                }

                return new ResponseEntity<>(candidatos, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    @PostMapping("/unCandidato")
    public ResponseEntity<?> obtenerCandidato(@RequestBody Long candidatoId) {
        try {
            Candidato candidato = filtroCandidatosService.obtenerElCandidato(candidatoId);

            if (candidato != null) {
                return new ResponseEntity<>(candidato, HttpStatus.OK);
            }else {
                throw new Exception("Candidato no encontrado");
            }


        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
