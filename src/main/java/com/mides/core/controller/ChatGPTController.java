package com.mides.core.controller;

import com.mides.core.request.EmpleoRequest;
import com.mides.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST)
public class ChatGPTController {
    @Autowired
    IEmpleoService empleoService;

    @PostMapping(value = "/filtroIA")
    public ResponseEntity<?> filterCandidatoByGpt(@RequestBody EmpleoRequest empleoRequest) throws IOException {
        return empleoService.candidatosSegueridosParaEmpleo(empleoRequest);
    }

}
