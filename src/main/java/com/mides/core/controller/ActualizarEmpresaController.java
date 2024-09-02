package com.mides.core.controller;

import com.mides.core.dto.*;
import com.mides.core.service.IActualizarEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actualizar")
@CrossOrigin(origins = "http://mides-web.s3-website-us-east-1.amazonaws.com", methods = { RequestMethod.PUT, RequestMethod.DELETE })
public class ActualizarEmpresaController {

    @Autowired
    IActualizarEmpresaService actualizarEmpresaService;

    @PutMapping("/empresa")
    public ResponseEntity<?> actualizarCampo(@RequestBody ActualizarEmpresaRequest request) {
        try {
            actualizarEmpresaService.actualizarCampo(request.getEmpresaId(), request.getEmpleoId(),request.getUserName(), request.getCampo(), request.getDatoAct(), request.getDatoAnt(),request.getLista(), request.getSubLista());
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/agregarASubListaEmpresa")
    public ResponseEntity<?> agrearASubLista(@RequestBody ActualizarSubListaEmpresaDTO request) {

        try {
            actualizarEmpresaService.agregarASubLista(request.getEmpresaId(), request.getEmpleoId(), request.getUserName(), request.getLista(), request.getSubLista(), request.getId());
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarSubListaEmpresa")
    public ResponseEntity<?> actualizarSubLista(@RequestBody EliminarSubListaEmpresaDTO request) {

        try {
            actualizarEmpresaService.elimnarSubLista(request.getEmpresaId(), request.getEmpleoId(), request.getUserName(), request.getLista(), request.getSubLista(), request.getId(), request.getNombreAEliminar(), request.getPosAElim());
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
