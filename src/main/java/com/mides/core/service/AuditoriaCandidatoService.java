package com.mides.core.service;

import com.mides.core.model.AuditoriaCandidato;
import com.mides.core.repository.IAuditoriaCandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaCandidatoService implements  IAuditoriaCandidatoService{

    @Autowired
    IAuditoriaCandidatoRepository auditoriaCandidatoRepository;

    @Override
    public void guardar(AuditoriaCandidato auditoriaCandidato) {
        auditoriaCandidatoRepository.save(auditoriaCandidato);

    }
}
