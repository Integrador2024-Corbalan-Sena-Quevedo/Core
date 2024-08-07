package com.mides.core.controller;

import com.mides.core.dto.SeguimientoDTO;
import com.mides.core.model.Seguimiento;
import com.mides.core.service.ISeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SeguimientoController {

    @Autowired
    ISeguimientoService seguimientoService;

    @PostMapping("/newSeguimiento")
    public ResponseEntity<?> saveSeguimiento(@RequestBody SeguimientoDTO seguimientoDTO){
        seguimientoService.processSeguimiento(seguimientoDTO);
        return new  ResponseEntity<>("Seguimiento guardado", HttpStatus.OK);
    }

    @PatchMapping("/updateSeguimiento")
    public ResponseEntity<?> updateSeguimiento(@RequestBody SeguimientoDTO seguimientoDTO){
        seguimientoService.processSeguimiento(seguimientoDTO);
        return new  ResponseEntity<>("Seguimiento actualizado", HttpStatus.OK);
    }


    @GetMapping("/allSeguimientos")
    public ResponseEntity<?> getSeguimientos(){

        List<SeguimientoDTO> seguimientos = seguimientoService.getSeguimientosDTO();

        if (!seguimientos.isEmpty()){
            return new ResponseEntity<>(seguimientos, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay seguimientos realizados", HttpStatus.BAD_REQUEST);
    }
}
