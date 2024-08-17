package com.mides.core.controller;

import com.mides.core.dto.ActualizarSubListaCandidatoDTO;
import com.mides.core.model.ActualizarCampoRequest;
import com.mides.core.service.IActualizarCandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actualizar")
@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.PUT, RequestMethod.DELETE })
public class ActualizarCandidatoController {

    @Autowired
    IActualizarCandidatoService actualizarCandidatoService;


    @PutMapping("/candidato")
    public ResponseEntity<?> actualizarCampo(@RequestBody ActualizarCampoRequest request) {
        System.out.println("LLEGUA A LA PARTE DE ACTUALIZAR");
        try {
            actualizarCandidatoService.actualizarCampo(request.getCandidatoId(), request.getCampo(), request.getDatoAct(), request.getDatoAnt(),request.getLista(), request.getSubLista());
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarSubLista")
    public ResponseEntity<?> actualizarSubLista(@RequestBody ActualizarSubListaCandidatoDTO request) {
        System.out.println("LLEGUA A LA PARTE DE ELIMINAR SUBLISTA");
        try {
            actualizarCandidatoService.elimnarSubLista(request.getCandidatoId(), request.getLista(), request.getSubLista(), request.getId());
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/agregarSubLista")
    public ResponseEntity<?> agrearASubLista(@RequestBody ActualizarSubListaCandidatoDTO request) {
        System.out.println("LLEGUA A LA PARTE DE AGREGAAR SUBLISTA");
        try {
            actualizarCandidatoService.agregarASubLista(request.getCandidatoId(), request.getLista(), request.getSubLista(), request.getId());
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
