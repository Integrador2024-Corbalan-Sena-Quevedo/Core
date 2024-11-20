package com.mides.core.controller;

import com.mides.core.dto.EmpleoDTO;
import com.mides.core.model.AuditoriaCandidato;
import com.mides.core.service.IAuditoriaCandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditoriasCandidatos")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.GET})

public class AuditoriasCandidatoController {
    @Autowired
    IAuditoriaCandidatoService auditoriaCandidatoService;

    @GetMapping("/obtener")
    public ResponseEntity<?> getAuditoriasCandidatos(){

        List<AuditoriaCandidato> auditoriaCandidatosDBO = auditoriaCandidatoService.getAuditorias();

        if (auditoriaCandidatosDBO.isEmpty()){
            return new ResponseEntity<>("No hay Auditorias disponibles", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(auditoriaCandidatosDBO, HttpStatus.OK);
        }
    }
}
