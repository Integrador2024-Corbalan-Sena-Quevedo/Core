package com.mides.core.controller;

import com.mides.core.model.Candidato;
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
@CrossOrigin(origins = "http://localhost:3000",  methods = RequestMethod.POST)
public class FiltroCandidatosController {
    @Autowired
    IFiltroCandidatosService filtroCandidatosService;

        @PostMapping("/candidatos")
        public ResponseEntity<?> filtrado(@RequestBody  Map<String, List<FiltroCandidato>> filtrosMap) {
            List<FiltroCandidato> filtros = filtrosMap.get("filtros");

            System.out.println("Lista de filtros:");
            for (FiltroCandidato filtro : filtros) {
                    System.out.println("  - " + filtro.toString());
                }
            try {
                SearchCandidatoSpecification criteria = new SearchCandidatoSpecification(filtros);
                Map<Long, Candidato> candidatos = filtroCandidatosService.filtrarCandidatos(criteria);
                System.out.println("Lista de filtros:");

                candidatos.forEach((id, candidato) ->
                        System.out.println("  - " + id.toString() + " - " + candidato.toString()));



                return new ResponseEntity<>(candidatos, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
