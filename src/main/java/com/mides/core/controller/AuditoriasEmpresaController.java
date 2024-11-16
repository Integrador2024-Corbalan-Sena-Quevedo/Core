package com.mides.core.controller;

import com.mides.core.model.AuditoriaEmpresa;
import com.mides.core.service.IAuditoriaEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auditoriasEmpresas")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.GET})
public class AuditoriasEmpresaController {
    @Autowired
    IAuditoriaEmpresaService auditoriaEmpresaService;

    @GetMapping("/obtener")
    public ResponseEntity<?> getAuditoriasCandidatos(){

        List<AuditoriaEmpresa> auditoriaEmpresaDBO = auditoriaEmpresaService.getAuditorias();

        if (auditoriaEmpresaDBO.isEmpty()){
            return new ResponseEntity<>("No hay Auditorias disponibles", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(auditoriaEmpresaDBO, HttpStatus.OK);
        }
    }
}
