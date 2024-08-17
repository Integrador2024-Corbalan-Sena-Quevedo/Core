package com.mides.core.controller;
import com.mides.core.model.Cliente;
import com.mides.core.model.Empresa;
import com.mides.core.service.IFiltroCandidatosService;
import com.mides.core.service.IFiltroEmpresaService;
import com.mides.core.specification.FiltroCandidato;
import com.mides.core.specification.FiltroEmpresa;
import com.mides.core.specification.SearchCandidatoSpecification;
import com.mides.core.specification.SearchEmpresaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/filtro")
@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.POST, RequestMethod.PUT })

public class FiltroEmpleosController {

    @Autowired
    IFiltroEmpresaService  filtroEmpresaService;

    private final ObjectMapper objectMapper;

    public FiltroEmpleosController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @PostMapping("/empleos")
    public ResponseEntity<?> filtradoEmpleos(@RequestBody  Map<String, List<FiltroEmpresa>> filtrosMap){

        List<FiltroEmpresa> filtros = filtrosMap.get("filtros");
        try {
            Map<Long, Empresa> empresas;
            if(filtros.isEmpty()){
                empresas = filtroEmpresaService.todasLasEmpresas();
            }else{
                SearchEmpresaSpecification criteria = new SearchEmpresaSpecification(filtros);
                empresas = filtroEmpresaService.filtrarEmresas(criteria);
            }
            return new ResponseEntity<>(empresas, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
