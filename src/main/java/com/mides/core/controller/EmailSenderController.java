package com.mides.core.controller;

import com.mides.core.model.Candidato;
import com.mides.core.request.EmailRequest;
import com.mides.core.service.EmailSenderService;
import com.mides.core.service.ICandidatoSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST)
public class EmailSenderController {
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ICandidatoSevice candidatoSevice;

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmails(@RequestBody EmailRequest request){
        List<Candidato> candidatos = new ArrayList<>();

        for (Long candidatoId : request.getCandidatosId()){
            Candidato candidato = candidatoSevice.findCandidatoById(candidatoId);
            if (candidato!= null){
                candidatos.add(candidato);
            }
        }
        if (!candidatos.isEmpty()){
            emailSenderService.sendEmailsToCompany(candidatos, request.getEmpresaId(), request.getEmpleoId(), request.getEmailUser());
            return new ResponseEntity<>("Se enviaron",  HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No hay candidatos para el envío",  HttpStatus.BAD_REQUEST);
        }

    }
}
