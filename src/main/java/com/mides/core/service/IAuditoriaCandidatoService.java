package com.mides.core.service;

import com.mides.core.model.AuditoriaCandidato;

import java.util.List;

public interface IAuditoriaCandidatoService {
    void guardar(AuditoriaCandidato auditoriaCandidato);

    List<AuditoriaCandidato> getAuditorias();
}
