package com.mides.core.controller;

import com.mides.core.dto.ActualizarSubListaCandidatoDTO;
import com.mides.core.dto.ActualizarCandidatoCampoRequest;
import com.mides.core.model.Idioma;
import com.mides.core.service.IActualizarCandidatoService;
import com.mides.core.service.IIdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actualizar")
@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.PUT, RequestMethod.DELETE })
public class ActualizarCandidatoController {

    @Autowired
    IActualizarCandidatoService actualizarCandidatoService;

    @Autowired
    IIdiomaService idiomaService;


    @PutMapping("/candidato")
    public ResponseEntity<?> actualizarCampo(@RequestBody ActualizarCandidatoCampoRequest request) {
        try {
            actualizarCandidatoService.actualizarCampo(request.getCandidatoId(), request.getCampo(), request.getUserName(), request.getDatoAct(), request.getDatoAnt(),request.getLista(), request.getSubLista());
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarSubLista")
    public ResponseEntity<?> actualizarSubLista(@RequestBody ActualizarSubListaCandidatoDTO request) {

        try {
            actualizarCandidatoService.elimnarSubLista(request.getCandidatoId(), request.getUserName(), request.getLista(), request.getSubLista(), request.getId());
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/agregarSubLista")
    public ResponseEntity<?> agrearASubLista(@RequestBody ActualizarSubListaCandidatoDTO request) {
        try {
            actualizarCandidatoService.agregarASubLista(request.getCandidatoId(), request.getUserName(), request.getLista(), request.getSubLista(), request.getId());
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/idiomasAll")
    public ResponseEntity<?> obtenerIdiomas() {
        try {
            List<Idioma> todosIdiomas = idiomaService.getIdiomas();
            return new ResponseEntity<>(todosIdiomas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
